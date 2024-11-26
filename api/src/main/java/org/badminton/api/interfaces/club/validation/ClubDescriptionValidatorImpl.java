package org.badminton.api.interfaces.club.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClubDescriptionValidatorImpl implements ConstraintValidator<ClubDescriptionValidator, String> {

	private static final int MIN_LENGTH = 2;
	private static final int MAX_LENGTH = 1200;

	@Override
	public void initialize(ClubDescriptionValidator constraintAnnotation) {
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
