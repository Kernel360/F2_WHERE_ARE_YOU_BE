package org.badminton.api.interfaces.member.validator;

import java.net.URL;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MemberImageValidatorImpl implements ConstraintValidator<MemberImageValidator, String> {

	private static final String S3_DOMAIN = "d36om9pjoifd2y.cloudfront.net";
	private static final Pattern IMAGE_PATTERN = Pattern.compile(
		".*\\.(png|jpg|jpeg|gif|avif|svg|webp|bmp|ico|tiff|tif|heic|heif|raw|cr2|nef|arw|dng|psd|ai|eps|pdf|jfif|jpe|svgz|xbm|pgm|pbm|ppm|pnm|webm|apng)$",
		Pattern.CASE_INSENSITIVE);

	@Override
	public void initialize(MemberImageValidator constraintAnnotation) {
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
				url.getPath().startsWith("/member-profile/") &&
				IMAGE_PATTERN.matcher(url.getPath()).matches();
		} catch (Exception e) {
			return false;
		}
	}
}
