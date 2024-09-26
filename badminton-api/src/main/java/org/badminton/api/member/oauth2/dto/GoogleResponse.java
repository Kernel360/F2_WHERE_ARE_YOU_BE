package org.badminton.api.member.oauth2.dto;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoogleResponse implements OAuthResponse {

	private final Map<String, Object> attribute;

	@Override
	public String getProvider() {
		return "google";
	}

	@Override
	public String getProviderId() {
		return attribute.get("sub").toString();
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
		return attribute.get("picture").toString();
	}
}
