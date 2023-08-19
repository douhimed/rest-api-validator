package com.sqli.intern.api.validator.utilities.validation;

import com.sqli.intern.api.validator.utilities.enums.OperationTypeEnum;
import com.sqli.intern.api.validator.utilities.validation.annotations.HttpMethodValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HttpMethodValidator implements ConstraintValidator<HttpMethodValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return OperationTypeEnum.isValidOperation(value);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
