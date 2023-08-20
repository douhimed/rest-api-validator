package com.sqli.intern.api.validator.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public final class ValidatorUtility {
    private ValidatorUtility(){
        throw new AssertionError("ValidatorUtility should not be instantiated.");
    }

    private static final List<String> BOOLEANS = Arrays.asList("TRUE", "FALSE");

    public static boolean isNumber(String value) {
        if (StringUtils.isBlank(value))
            return false;
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isString(String value) {
        return StringUtils.isNotBlank(value)
                && !isNumber(value)
                && !Boolean.parseBoolean(value);
    }

    public static boolean isBoolean(String value) {
        return StringUtils.isNotBlank(value) && BOOLEANS.contains(value.toUpperCase());
    }

    public static boolean isJson(String value) {
        try {
            new ObjectMapper().readTree(value);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
    public static boolean isVoid(String value) {
        return StringUtils.isBlank(value);
    }
    
}
