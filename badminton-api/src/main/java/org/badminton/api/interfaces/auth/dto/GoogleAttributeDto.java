package org.badminton.api.interfaces.auth.dto;

import java.util.Map;

public record GoogleAttributeDto(
	String providerName,
	String sub,
	String email,
	String name,
	String picture
) {
	public static GoogleAttributeDto fromAttributes(Map<String, Object> attributes) {
		return new GoogleAttributeDto(
			"google",
			(String)attributes.get("sub"),
			(String)attributes.get("email"),
			(String)attributes.get("name"),
			(String)attributes.get("picture")
		);
	}
}
