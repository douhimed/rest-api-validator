package com.sqli.intern.api.validator.utilities.mappers;

import com.sqli.intern.api.validator.entities.ProjectEntity;
import com.sqli.intern.api.validator.utilities.dtos.ProjectDto;

import static com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum.INSTANTIATION_NOT_ALLOWED;

public final class ProjectMapper {

    private ProjectMapper() throws InstantiationException {
        throw new InstantiationException(INSTANTIATION_NOT_ALLOWED.getMessage());
    }

    public static ProjectDto map(ProjectEntity projectEntity) {
        return ProjectDto.builder()
                .id(projectEntity.getId())
                .name(projectEntity.getName())
                .withAuth(projectEntity.getWithAuth())
                .operationDtos(OperationMapper.map(projectEntity.getOperations()))
                .build();
    }

    public static ProjectEntity from(ProjectDto projectDto) {
        return ProjectEntity.builder()
                .id(projectDto.getId())
                .name(projectDto.getName())
                .withAuth(projectDto.isWithAuth())
                .build();
    }
}
