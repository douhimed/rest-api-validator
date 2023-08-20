package com.sqli.intern.api.validator.utilities.enums;

public enum ValidationStatus {
    VALID, INVALID,BAD_REQUEST;

    public boolean isInvalid() {
        return this == INVALID;
    }
}
