package com.sqli.intern.api.validator.services;

import com.sqli.intern.api.validator.utilities.ValidatorUtility;
import com.sqli.intern.api.validator.utilities.enums.OperationTypeEnum;
import com.sqli.intern.api.validator.utilities.exceptions.OperationException;
import org.apache.commons.lang3.StringUtils;

import static com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum.*;


public final class OperationRules {

    private OperationRules(){
        throw new AssertionError("OperationRules should not be instantiated.");
    }

    public static boolean isBodyBlank(String body) {
        if (StringUtils.isBlank(body))
            throw new OperationException(BODY_NULL);
        return true;
    }

    public static boolean isExpectedResponseBlank(String expectedResponse) {
        if (StringUtils.isBlank(expectedResponse))
            throw new OperationException(EXPECTED_RESPONSE_NULL);
        return true;
    }

    public static boolean isJsonValid(String value) {
        if (!ValidatorUtility.isJson(value))
            throw new OperationException(NOT_VALID_JSON);
        return true;

    }

    public static boolean isHttpMethodAllowed(String httpMethod) {
        if (!OperationTypeEnum.isValidOperation(httpMethod))
            throw new OperationException(NOT_VALID_HTTP_METHOD);
        return true;
    }
    public static boolean isUrlBlank(String url) {
        if (StringUtils.isBlank(url))
            throw new OperationException(URL_NULL);
        return true;
    }
}
