package org.badminton.api.interfaces.auth.dto;

import java.util.Map;

public record NaverAttributeDto(
	String providerName,
	String id,
	String email,
	String name,
	String profileImage
) {
	public static NaverAttributeDto fromAttributes(Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>)attributes.get("response");
		return new NaverAttributeDto(
			"naver",
			(String)response.get("id"),
			(String)response.get("email"),
			(String)response.get("name"),
			(String)response.get("profile_image")
		);
	}
}
