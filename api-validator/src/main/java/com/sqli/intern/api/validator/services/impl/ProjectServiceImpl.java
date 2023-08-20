package com.sqli.intern.api.validator.services.impl;

import com.sqli.intern.api.validator.utilities.mappers.RequestResponseMapper;
import com.sqli.intern.api.validator.utilities.models.AuthHeaderProvider;
import com.sqli.intern.api.validator.entities.ProjectEntity;
import com.sqli.intern.api.validator.repositories.ProjectRepository;
import com.sqli.intern.api.validator.services.OperationService;
import com.sqli.intern.api.validator.services.ProjectService;
import com.sqli.intern.api.validator.utilities.dtos.ProjectDto;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;
import com.sqli.intern.api.validator.utilities.exceptions.ProjectException;
import com.sqli.intern.api.validator.utilities.exceptions.TestRunException;
import com.sqli.intern.api.validator.utilities.mappers.OperationMapper;
import com.sqli.intern.api.validator.utilities.mappers.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum.NAME_ALREADY_EXIST;
import static com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum.PROJECT_NOT_FOUND;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OperationService operationService;
    @Autowired
    Environment env;
    @Autowired
    ValidationContext validationContext;

    @Override
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAllByDeletedFalse()
                .stream()
                .map(projectEntity -> {
                    ProjectDto projectDto = new ProjectDto();
                    projectDto.setId(projectEntity.getId());
                    projectDto.setName(projectEntity.getName());
                    return projectDto;
                })
                .toList();
    }

    public List<ProjectDto> getAllProjectsWithOperations() {
        return projectRepository.findAllByDeletedFalse()
                .stream()
                .map(projectEntity -> {
                    ProjectDto projectDto = new ProjectDto();
                    projectDto.setId(projectEntity.getId());
                    projectDto.setName(projectEntity.getName());
                    projectDto.setOperationDtos(OperationMapper.map(projectEntity.getOperations()));
                    return projectDto;
                })
                .toList();
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        return projectRepository.findByIdAndDeletedFalse(id)
                .map(ProjectMapper::map)
                .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));
    }

    @Override
    public Long addProject(ProjectDto projectDto) {
        checkIfNameAlreadyExist(projectDto.getName());
        ProjectEntity projectEntity = ProjectMapper.from(projectDto);
        return projectRepository.save(projectEntity).getId();
    }

    @Override
    public Long updateProject(Long id, ProjectDto projectDto) {
        ProjectEntity project = getProjectEntityOrThrowExceptionIfNotFound(id);
        project.setName(projectDto.getName());
        project.setWithAuth(projectDto.isWithAuth());
        projectRepository.save(project);
        return id;
    }

    @Override
    public Long deleteProject(Long id) {
        ProjectEntity project = getProjectEntityOrThrowExceptionIfNotFound(id);
        project.setDeleted(true);
        projectRepository.save(project);
        return id;
    }

    @Override
    public ProjectDto runProjectTests(Long id) {
        ProjectEntity project = getProjectEntityOrThrowExceptionIfNotFound(id);
        final AuthHeaderProvider authHeaderProvider = setAuthHeaderProvider(project);
        List<ResponseDto> responseDtos = project.getOperations().stream()
                .map(OperationMapper::map)
                .map(operation -> {
                    try {
                        return operationService.runTest(operation, authHeaderProvider);
                    } catch (InstantiationException e) {
                        throw new TestRunException(e.getMessage());
                    }
                })
                .toList();
        ProjectDto projectDto = ProjectMapper.map(project);
        projectDto.setResponseDto(responseDtos);
        return projectDto;
    }

    private AuthHeaderProvider setAuthHeaderProvider(ProjectEntity project) {
        String username = env.getProperty(project.getName() + ".username");
        String password = env.getProperty(project.getName() + ".password");
        return new AuthHeaderProvider(username, password);
    }


    public ProjectEntity getProjectEntityOrThrowExceptionIfNotFound(Long id) {
        return projectRepository.findById(id)
                .filter(project -> !project.isDeleted())
                .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));
    }

    private void checkIfNameAlreadyExist(String name) {
        projectRepository.findByName(name)
                .ifPresent(project -> {
                    throw new ProjectException(NAME_ALREADY_EXIST);
                });
    }

    public ProjectDto compareJsonAndValidate(Long id) {
        ProjectDto projectDto = getProjectById(id);
        List<ResponseDto> responseDtos = projectDto.getOperationDtos().stream()
                .map(RequestResponseMapper::map).toList();
        projectDto.setResponseDto(responseDtos);

        for (ResponseDto responseDto : responseDtos) {
            String requestType = responseDto.getType();
            validationContext.compareJson(requestType, responseDto);
        }
        projectDto.setOperationDtos(null);
        return projectDto;
    }
}