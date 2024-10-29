package org.badminton.api.interfaces.club.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClubNameValidatorImpl implements ConstraintValidator<ClubNameValidator, String> {

    @Override
    public void initialize(ClubNameValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return value.trim().length() >= 2 && value.trim().length() <= 20;
    }
}
