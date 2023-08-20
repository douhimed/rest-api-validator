package com.sqli.intern.api.validator.services;

import com.sqli.intern.api.validator.utilities.models.AuthHeaderProvider;
import com.sqli.intern.api.validator.utilities.dtos.OperationDto;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;
import com.sqli.intern.api.validator.utilities.enums.ValidationStatus;

import java.util.List;

public interface OperationService {
    ValidationStatus compareJson(OperationDto operationDto);


    ResponseDto runTest(OperationDto operationDto) throws InstantiationException;
    ResponseDto runTest(OperationDto operationDto, AuthHeaderProvider authHeaderProvider) throws InstantiationException;

    List<OperationDto> getAllOperations();

    OperationDto getOperationById(Long id);

    Long addOperation(OperationDto operationDto);

    Long updateOperation(Long id, OperationDto operationDto);
    Long updateExcpectedResponse(Long id,String newExcpectedResponse);

    Long deleteOperation(Long id);

    void updateActualResponseAndHttpStatus(OperationDto operationDto, AuthHeaderProvider authHeaderProvider) throws InstantiationException;
}
