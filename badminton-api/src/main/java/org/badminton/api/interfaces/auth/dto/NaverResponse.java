package org.badminton.api.interfaces.auth.dto;

import java.util.Map;

public class NaverResponse implements OAuthResponse {

	private final NaverAttributeDto attribute;

	public NaverResponse(Map<String, Object> attribute) {
		this.attribute = NaverAttributeDto.fromAttributes(attribute);
	}

	@Override
	public String getProvider() {
		return attribute.providerName();
	}

	@Override
	public String getProviderId() {
		return attribute.id();
	}

	@Override
	public String getEmail() {
		return attribute.email();
	}

	@Override
	public String getName() {
		return attribute.name();
	}

	public String getProfileImage() {
		return attribute.profileImage();
	}

}
