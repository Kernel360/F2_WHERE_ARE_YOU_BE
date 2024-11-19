package org.badminton.api.interfaces.member.validator;

import java.net.URL;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberImageValidatorImpl implements ConstraintValidator<MemberImageValidator, String> {

	public static final String NAVER_DOMAIN = "pstatic.net";
	public static final String GOOGLE_DOMAIN = "googleusercontent.com";
	public static final String KAKAO_DOMAIN = "kakaocdn.net";
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

			// 허용된 호스트 목록
			String host = url.getHost();
			boolean isAllowedHostWithoutExtensionCheck =
				host.endsWith(NAVER_DOMAIN) ||  // 네이버 도메인
					host.endsWith(GOOGLE_DOMAIN) ||  // 구글 도메인
					host.endsWith(KAKAO_DOMAIN);  // 카카오 도메인 (모든 하위 도메인 포함)

			boolean isS3Valid = host.equals(S3_DOMAIN)
				&& url.getPath().startsWith("/member-profile/")
				&& IMAGE_PATTERN.matcher(url.getPath()).matches();

			return isS3Valid || isAllowedHostWithoutExtensionCheck;
		} catch (Exception exception) {
			log.error(String.valueOf(exception));
			return false;
		}
	}
}
