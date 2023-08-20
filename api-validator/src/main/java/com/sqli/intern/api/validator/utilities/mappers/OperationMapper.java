package com.sqli.intern.api.validator.utilities.mappers;

import com.sqli.intern.api.validator.entities.OperationEntity;
import com.sqli.intern.api.validator.entities.ProjectEntity;
import com.sqli.intern.api.validator.utilities.dtos.OperationDto;

import java.util.List;

import static com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum.INSTANTIATION_NOT_ALLOWED;

public final class OperationMapper {
    private OperationMapper() throws InstantiationException {
        throw new InstantiationException(INSTANTIATION_NOT_ALLOWED.getMessage());
    }

    public static OperationDto map(OperationEntity operationEntity) {
        return OperationDto.builder()
                .id(operationEntity.getId())
                .url(operationEntity.getUrl())
                .body(operationEntity.getBody())
                .type(operationEntity.getType())
                .actualResponse(operationEntity.getActualResponse())
                .expectedResponse(operationEntity.getExpectedResponse())
                .expectedType(operationEntity.getExpectedType())
                .httpStatus(operationEntity.getHttpStatus())
                .build();
    }

    public static List<OperationDto> map(List<OperationEntity> operationEntities) {
        return operationEntities.stream()
                .map(OperationMapper::map)
                .toList();
    }

    public static OperationEntity from(OperationDto operationDto) {
        return OperationEntity.builder()
                .url(operationDto.getUrl())
                .body(operationDto.getBody())
                .type(operationDto.getType())
                .expectedResponse(operationDto.getExpectedResponse())
                .actualResponse(operationDto.getActualResponse())
                .expectedType(operationDto.getExpectedType())
                .project(ProjectEntity.builder().id(operationDto.getProjectId()).build())
                .httpStatus(operationDto.getHttpStatus())
                .build();
    }

    public static List<OperationEntity> from(List<OperationDto> operationDtos) {
        return operationDtos.stream()
                .map(OperationMapper::from)
                .toList();
    }

    public static void updateOperationEntity(OperationDto operationDto, OperationEntity operationEntity) {
        operationEntity.setUrl(operationDto.getUrl());
        operationEntity.setBody(operationDto.getBody());
        operationEntity.setType(operationDto.getType());
        operationEntity.setExpectedType(operationDto.getExpectedType());
        operationEntity.setExpectedResponse(operationDto.getExpectedResponse());
        operationEntity.setActualResponse(operationDto.getActualResponse());
        operationEntity.setHttpStatus(operationDto.getHttpStatus());
    }
}
