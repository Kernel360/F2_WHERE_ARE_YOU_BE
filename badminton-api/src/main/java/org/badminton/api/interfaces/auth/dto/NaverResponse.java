package org.badminton.api.interfaces.auth.dto;

import java.util.Map;

public class NaverResponse implements OAuthResponse {

	private final Map<String, Object> attribute;

	public NaverResponse(Map<String, Object> attribute) {

		this.attribute = (Map<String, Object>)attribute.get("response");
	}

	@Override
	public String getProvider() {
		return "naver";
	}

	@Override
	public String getProviderId() {
		return attribute.get("id").toString();
	}

	@Override
	public String getEmail() {
		return attribute.get("email").toString();
	}

	@Override
	public String getName() {
		return attribute.get("name").toString();
	}

	public String getProfileImage() {
		return attribute.get("profile_image").toString();
	}

}
