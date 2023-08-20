package com.sqli.intern.api.validator.utilities.validation;

import com.sqli.intern.api.validator.utilities.ValidatorUtility;
import com.sqli.intern.api.validator.utilities.validation.annotations.JsonValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class JsonValidator implements ConstraintValidator<JsonValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ValidatorUtility.isJson(value);
    }
}
