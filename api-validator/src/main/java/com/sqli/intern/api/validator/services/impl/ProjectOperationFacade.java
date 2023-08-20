package com.sqli.intern.api.validator.services.impl;

import com.sqli.intern.api.validator.services.OperationService;
import com.sqli.intern.api.validator.services.ProjectService;
import com.sqli.intern.api.validator.utilities.dtos.OperationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectOperationFacade {
    @Autowired
    ProjectService projectService;
    @Autowired
    OperationService operationService;

    public Long createOperation(Long projectId, OperationDto operationDto) {
        projectService.getProjectEntityOrThrowExceptionIfNotFound(projectId);
        return operationService.addOperation(operationDto);
    }
}
