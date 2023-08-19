package com.sqli.intern.api.validator.services;

import com.sqli.intern.api.validator.utilities.dtos.OperationDto;

public interface OperationValidator {
    boolean validate(OperationDto operationDto);

}
