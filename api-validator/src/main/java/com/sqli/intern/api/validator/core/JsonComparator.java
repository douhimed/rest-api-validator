package com.sqli.intern.api.validator.core;

import com.sqli.intern.api.validator.utilities.dtos.ResponseDto;

import java.util.Set;

public interface JsonComparator {
    void compareJson(ResponseDto responseDto);

    Set<String> getSupportedRequestTypes();
}
