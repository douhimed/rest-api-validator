package com.sqli.intern.api.validator.services;

import com.sqli.intern.api.validator.entities.ProjectEntity;
import com.sqli.intern.api.validator.utilities.dtos.ProjectDto;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAllProjects();

    List<ProjectDto> getAllProjectsWithOperations();

    ProjectDto getProjectById(Long id);

    Long addProject(ProjectDto projectDto);

    Long updateProject(Long id, ProjectDto projectDto);

    Long deleteProject(Long id);

    ProjectDto runProjectTests(Long id);

    ProjectEntity getProjectEntityOrThrowExceptionIfNotFound(Long id);
    ProjectDto compareJsonAndValidate(Long id);
}
