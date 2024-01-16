package com.svoiapp.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileNumberValidator implements ConstraintValidator<StartsWith, String> {

    private String prefix;

    @Override
    public void initialize(StartsWith constraintAnnotation) {
        prefix = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.startsWith(prefix);
    }
}
