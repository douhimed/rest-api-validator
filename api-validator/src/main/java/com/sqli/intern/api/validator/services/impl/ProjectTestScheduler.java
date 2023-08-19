package com.sqli.intern.api.validator.services.impl;

import com.sqli.intern.api.validator.services.OperationService;
import com.sqli.intern.api.validator.services.ProjectService;
import com.sqli.intern.api.validator.utilities.dtos.ProjectDto;
import com.sqli.intern.api.validator.utilities.models.AuthHeaderProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class ProjectTestScheduler {
    @Autowired
    ProjectService projectService;

    @Autowired
    OperationService operationService;

    @Autowired
    private Environment env;

    @Scheduled(fixedRate = 86_400_000)
    public void runProjectTest() {
        projectService.getAllProjectsWithOperations()
                .stream()
                .forEach(projectDto -> {
                    if (CollectionUtils.isNotEmpty(projectDto.getOperationDtos()))
                            processProject(projectDto);
                });
    }

    private void processProject(ProjectDto project) {
        final AuthHeaderProvider authHeaderProvider = setAuthHeaderProvider(project);
        project.getOperationDtos().forEach(operation -> {
            try {
                this.operationService.updateActualResponseAndHttpStatus(operation, authHeaderProvider);
                log.info("operation id : " + operation.getId() + " started");
            } catch (InstantiationException e) {
                log.warn(e.getMessage());
            }
        });
    }

    private AuthHeaderProvider setAuthHeaderProvider(ProjectDto project) {
        String username = env.getProperty(project.getName() + ".username");
        String password = env.getProperty(project.getName() + ".password");
        return new AuthHeaderProvider(username, password);
    }

}

