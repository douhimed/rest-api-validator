package com.sqli.intern.api.validator.utilities.validation.annotations;

import com.sqli.intern.api.validator.utilities.validation.JsonValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JsonValidator.class)
public @interface JsonValid {
    String message() default "Invalid JSON";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
