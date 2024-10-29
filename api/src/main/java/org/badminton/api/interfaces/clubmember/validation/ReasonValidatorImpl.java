package org.badminton.api.interfaces.clubmember.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReasonValidatorImpl implements ConstraintValidator<ReasonValidator, String> {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 100;

    @Override
    public void initialize(ReasonValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return value.trim().length() >= MIN_LENGTH && value.trim().length() <= MAX_LENGTH;
    }
}
