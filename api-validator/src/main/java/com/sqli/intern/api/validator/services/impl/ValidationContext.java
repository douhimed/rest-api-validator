package com.sqli.intern.api.validator.services.impl;

import com.sqli.intern.api.validator.core.JsonComparator;
import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationContext {

    private final List<JsonComparator> validators;

    @Autowired
    public ValidationContext(List<JsonComparator> validators) {
        this.validators = validators;
    }

    public void compareJson(String requestType, ResponseDto responseDto) {
        JsonComparator validator = findValidatorByRequestType(requestType.toUpperCase());
        validator.compareJson(responseDto);
    }

    private JsonComparator findValidatorByRequestType(String requestType) {
        return validators.stream()
                .filter(validator -> validator.getSupportedRequestTypes().contains(requestType.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported request type: " + requestType));
    }
}
