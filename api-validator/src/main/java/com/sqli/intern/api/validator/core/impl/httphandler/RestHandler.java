package com.sqli.intern.api.validator.core.impl.httphandler;

import com.sqli.intern.api.validator.utilities.dtos.OperationDto;
import com.sqli.intern.api.validator.utilities.enums.ValidationStatus;
import com.sqli.intern.api.validator.utilities.mappers.RequestResponseMapper;
import com.sqli.intern.api.validator.utilities.models.AuthHeaderProvider;
import com.sqli.intern.api.validator.core.RestCaller;
import com.sqli.intern.api.validator.core.impl.OperationHandler;
import com.sqli.intern.api.validator.core.impl.jsonhandler.JsonHandler;
import com.sqli.intern.api.validator.utilities.dtos.ReportDto;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;
import com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


public abstract class RestHandler extends OperationHandler implements RestCaller {

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    protected JsonHandler commandValidator;

    @Autowired
    protected JsonHandler queryValidator;

    public abstract HttpMethod getType();

    public abstract <T> HttpEntity<T> getBody(ResponseDto responseDto);

    @Override
    public void runTest(ResponseDto responseDto) {
        invoke(responseDto);
    }

    @Override
    public void runTest(ResponseDto responseDto, AuthHeaderProvider authHeaderProvider) {
        try {
            callApi(responseDto, authHeaderProvider);
        } catch (HttpClientErrorException e) {
            setExceptionMessage(responseDto, e);
        }
    }

    @Override
    public void invoke(ResponseDto responseDto) {
        invoke(responseDto, null);
    }

    @Override
    public void invoke(ResponseDto responseDto, AuthHeaderProvider authHeaderProvider) {
        try {
            callApi(responseDto, authHeaderProvider);
            invokeNext(responseDto, null);
        } catch (HttpClientErrorException e) {
            responseDto.setValidationStatus(ValidationStatus.BAD_REQUEST);
            responseDto.setHttpStatus(String.valueOf(e.getStatusCode().value()));
            setExceptionMessage(responseDto, e);
        }
    }

    private static void setExceptionMessage(ResponseDto responseDto, HttpClientErrorException e) {
        String errorMessage = e.getStatusCode().is5xxServerError()
                ? ExceptionMessageEnum.SERVICE_NOT_FOUND.getMessage()
                : ExceptionMessageEnum.BAD_REQUEST.getMessage();

        responseDto.addMessage(ReportDto.createErrorMessage(errorMessage));
    }


    private void callApi(ResponseDto responseDto, AuthHeaderProvider authHeaderProvider) {
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(responseDto.getUrl().trim(),
                    getType(),
                    new HttpEntity<>(getBody(responseDto), authHeaderProvider.setHeader()),
                    String.class);
            responseDto.setHttpStatus(String.valueOf(responseEntity.getStatusCode().value()));
            updateHttpStatusOfOperation(responseDto);
            responseDto.setActualResponse(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            responseDto.setHttpStatus(String.valueOf(e.getStatusCode().value()));
            responseDto.setValidationStatus(ValidationStatus.BAD_REQUEST);
        }

    }

    private static void updateHttpStatusOfOperation(ResponseDto responseDto) {
        OperationDto operationDto = RequestResponseMapper.from(responseDto);
        operationDto.setHttpStatus(responseDto.getHttpStatus());
    }

}