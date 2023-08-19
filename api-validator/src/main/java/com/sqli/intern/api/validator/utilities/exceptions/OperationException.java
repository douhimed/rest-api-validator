package com.sqli.intern.api.validator.utilities.exceptions;

import com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum;

public class OperationException extends RuntimeException {
    public OperationException(String message) {
        super(message);
    }

    public OperationException(ExceptionMessageEnum raison) {
        super(String.valueOf(raison));
    }
}
