package com.sqli.intern.api.validator.utilities.exceptions;

import com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum;

public class ProjectException extends RuntimeException {
    public ProjectException(String message) {
        super(message);
    }

    public ProjectException(ExceptionMessageEnum reason) {
        this(reason.getMessage());
    }
}
