package org.badminton.api.interfaces.league.validation.validator;

import java.util.Objects;

import org.badminton.api.interfaces.league.validation.annotation.LeagueDescriptionValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LeagueDescriptionValidatorImpl implements ConstraintValidator<LeagueDescriptionValidator, String> {

    private static final int MIN_LENGTH = 15;
    private static final int MAX_LENGTH = 1000;

    @Override
    public void initialize(LeagueDescriptionValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String leagueDescription, ConstraintValidatorContext context) {
        if (Objects.isNull(leagueDescription) || leagueDescription.isBlank()) {
            return false;
        }
        return isLengthValid(leagueDescription);
    }

    private boolean isLengthValid(String leagueDescription) {
        int length = leagueDescription.length();
        return length >= MIN_LENGTH && length <= MAX_LENGTH;
    }
}