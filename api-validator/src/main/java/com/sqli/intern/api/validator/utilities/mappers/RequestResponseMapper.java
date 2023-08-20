package com.sqli.intern.api.validator.utilities.mappers;

import com.sqli.intern.api.validator.utilities.dtos.OperationDto;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;

import static com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum.INSTANTIATION_NOT_ALLOWED;

public final class RequestResponseMapper {

    private RequestResponseMapper() throws InstantiationException {
        throw new InstantiationException(INSTANTIATION_NOT_ALLOWED.getMessage());
    }

    public static ResponseDto map(OperationDto operationDto) {
        return ResponseDto.builder()
                .id(operationDto.getId())
                .url(operationDto.getUrl())
                .type(operationDto.getType())
                .body(operationDto.getBody())
                .expectedResponse(operationDto.getExpectedResponse())
                .actualResponse(operationDto.getActualResponse())
                .expectedType(operationDto.getExpectedType())
                .httpStatus(operationDto.getHttpStatus())
                .build();
    }

    public static OperationDto from(ResponseDto responseDto) {
        return OperationDto.builder()
                .httpStatus(responseDto.getHttpStatus())
                .build();
    }
}
