package org.badminton.api.interfaces.auth.dto;

import java.util.Map;

public record KakaoAttributeDto(
	String providerName,
	String id,
	KakaoAccountDto kakaoAccountDto
) {
	public static KakaoAttributeDto fromAttributes(Map<String, Object> attributes) {

		return new KakaoAttributeDto(
			"kakao",
			attributes.get("id").toString(),
			KakaoAccountDto.fromKakaoAccount((Map<String, Object>)attributes.get("kakao_account"))
		);
	}
}
