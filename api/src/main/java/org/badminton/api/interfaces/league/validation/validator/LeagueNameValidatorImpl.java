package org.badminton.api.interfaces.league.validation.validator;

import java.util.Objects;

import org.badminton.api.interfaces.league.validation.annotation.LeagueNameValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LeagueNameValidatorImpl implements ConstraintValidator<LeagueNameValidator, String> {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 20;

    @Override
    public void initialize(LeagueNameValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String leagueName, ConstraintValidatorContext context) {
        if (Objects.isNull(leagueName) || leagueName.isBlank()) {
            return false;
        }
        return isLengthValid(leagueName);
    }

    private boolean isLengthValid(String leagueName) {
        int length = leagueName.length();
        return length >= MIN_LENGTH && length <= MAX_LENGTH;  // 길이가 2 이상 20 이하일 때만 유효
    }
}