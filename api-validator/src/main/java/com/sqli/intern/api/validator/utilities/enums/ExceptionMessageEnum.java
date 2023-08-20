package com.sqli.intern.api.validator.utilities.enums;

public enum
ExceptionMessageEnum {
    SERVICE_NOT_FOUND("SERVICE NOT FOUND"),
    BAD_REQUEST("BAD REQUEST"),
    NAME_ALREADY_EXIST("NAME ALREADY EXIST"),
    PROJECT_NOT_FOUND("PROJECT NOT FOUND"),
    NOT_FOUND_OPERATION("OPERATION NOT FOUND"),
    BODY_NULL("BODY NULL"),
    EXPECTED_RESPONSE_NULL("EXPECTED RESPONSE NULL"),
    NOT_VALID_JSON("JSON NOT VALID"),
    NOT_VALID_EXPECTED_RESPONSE_TYPE("EXPECTED TYPE NOT VALID"),
    NOT_VALID_HTTP_METHOD("NOT VALID HTTP METHOD"),
    NODE_NOT_FOUND("NODE NOT FOUND"),
    NOT_VALUE_NODE("NOT VALUE NODE"),
    URL_NULL("URL NULL"),
    INSTANTIATION_NOT_ALLOWED("INSTANTIATION NOT ALLOWED");

    private final String message;

    ExceptionMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
