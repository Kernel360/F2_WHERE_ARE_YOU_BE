package org.badminton.api.interfaces.auth.dto;

import java.util.Map;

public record KakaoProfileDto(
	String nickname,
	String profileImageUrl
) {
	public static KakaoProfileDto fromProfile(Map<String, Object> profile) {
		return new KakaoProfileDto(
			(String)profile.get("nickname"),
			(String)profile.get("profile_image_url")
		);
	}
}
