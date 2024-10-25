package org.badminton.api.interfaces.club.validation;

import java.net.URL;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClubImageValidatorImpl implements ConstraintValidator<ClubImageValidator, String> {

	private static final String S3_DOMAIN = "d36om9pjoifd2y.cloudfront.net";
	private static final Pattern IMAGE_PATTERN = Pattern.compile(
		".*\\.(png|jpg|jpeg|gif|avif|svg|webp|bmp|ico|tiff|tif|heic|heif|raw|cr2|nef|arw|dng|psd|ai|eps|pdf|jfif|jpe|svgz|xbm|pgm|pbm|ppm|pnm|webm|apng)$",
		Pattern.CASE_INSENSITIVE);

	@Override
	public void initialize(ClubImageValidator constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.trim().isEmpty()) {
			return false;
		}
		try {
			URL url = new URL(value);
			return url.getHost().equals(S3_DOMAIN) &&
				url.getPath().startsWith("/club-banner/") &&
				IMAGE_PATTERN.matcher(url.getPath()).matches();
		} catch (Exception e) {
			return false;
		}
	}
}
