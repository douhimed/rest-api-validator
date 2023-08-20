package com.sqli.intern.api.validator.utilities.enums;

import com.sqli.intern.api.validator.utilities.exceptions.OperationException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum OperationTypeEnum {
    GET, POST, DELETE, PUT, PATCH, OPTION;

    private static final List<OperationTypeEnum> VALID_OPERATIONS;

    static {
        VALID_OPERATIONS = new ArrayList<>();
        VALID_OPERATIONS.add(GET);
        VALID_OPERATIONS.add(PUT);
        VALID_OPERATIONS.add(DELETE);
        VALID_OPERATIONS.add(POST);
    }

    public static boolean isTypeGet(String type) {
        return GET.name().equalsIgnoreCase(type);
    }

    public static boolean isTypePost(String type) {
        return POST.name().equalsIgnoreCase(type);
    }

    public static boolean isTypePut(String type) {
        return PUT.name().equalsIgnoreCase(type);
    }


    public static boolean isTypeDelete(String type) {
        return DELETE.name().equalsIgnoreCase(type);
    }

    public static boolean isValidOperation(String operation) {
        return StringUtils.isNotBlank(operation)
                && VALID_OPERATIONS.contains(OperationTypeEnum.valueOf(operation.toUpperCase()));
    }

    public static OperationTypeEnum from(String type) {
        return Arrays.stream(OperationTypeEnum.values()).filter(method -> method.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new OperationException("OPERATION NOT FOUND"));
    }
}
