package com.sqli.intern.api.validator.utilities.exceptions;

import com.sqli.intern.api.validator.utilities.enums.ExceptionMessageEnum;

public class JsonNodeException extends RuntimeException {
    public JsonNodeException(String message) {
        super(message);
    }

    public JsonNodeException(ExceptionMessageEnum reason) {
        this(reason.getMessage());
    }

}
