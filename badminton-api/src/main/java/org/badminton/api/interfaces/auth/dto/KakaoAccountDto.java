package org.badminton.api.interfaces.auth.dto;

import java.util.Map;

public record KakaoAccountDto(
	String email,
	KakaoProfileDto profile
) {
	public static KakaoAccountDto fromKakaoAccount(Map<String, Object> kakaoAccount) {
		return new KakaoAccountDto(
			(String)kakaoAccount.get("email"),
			KakaoProfileDto.fromProfile((Map<String, Object>)kakaoAccount.get("profile"))
		);
	}
}
