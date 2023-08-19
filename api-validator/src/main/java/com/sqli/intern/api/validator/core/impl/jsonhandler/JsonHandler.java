package com.sqli.intern.api.validator.core.impl.jsonhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqli.intern.api.validator.utilities.enums.ValidationStatus;
import com.sqli.intern.api.validator.utilities.models.AuthHeaderProvider;
import com.sqli.intern.api.validator.core.JsonComparator;
import com.sqli.intern.api.validator.core.impl.OperationHandler;
import com.sqli.intern.api.validator.utilities.dtos.ReportDto;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class JsonHandler extends OperationHandler implements JsonComparator {

    @Autowired
    protected ObjectMapper objectMapper;


    @Override
    public void invoke(ResponseDto responseDto) {
        try {
            JsonNode expectedJsonNode = objectMapper.readTree(responseDto.getExpectedResponse());
            invokeValidation(expectedJsonNode, responseDto);
        } catch (JsonProcessingException e) {
            responseDto.addMessage(ReportDto.createErrorMessage("INVALID FORMAT"));
        }
    }
    @Override
    public void invoke(ResponseDto responseDto, AuthHeaderProvider authHeaderProvider) {
        try {
            JsonNode expectedJsonNode = objectMapper.readTree(responseDto.getExpectedResponse());
            invokeValidation(expectedJsonNode, responseDto);
        } catch (JsonProcessingException e) {
            responseDto.addMessage(ReportDto.createErrorMessage("INVALID FORMAT"));
        }
    }

    public void compareJson(ResponseDto responseDto) {
        invoke(responseDto);
    }

    protected abstract void invokeValidation(JsonNode expected, ResponseDto responseDto);

    @Override
    public JsonHandler getNext() {
        return null;
    }
}
