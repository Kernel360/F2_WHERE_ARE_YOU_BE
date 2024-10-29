package org.badminton.api.interfaces.auth.dto;

import java.util.Map;

public class GoogleResponse implements OAuthResponse {

	private final GoogleAttributeDto attributes;

	public GoogleResponse(Map<String, Object> attributes) {
		this.attributes = GoogleAttributeDto.fromAttributes(attributes);
	}

	@Override
	public String getProvider() {
		return attributes.providerName();
	}

	@Override
	public String getProviderId() {
		return attributes.sub();
	}

	@Override
	public String getEmail() {
		return attributes.email();
	}

	@Override
	public String getName() {
		return attributes.name();
	}

	public String getProfileImage() {
		return attributes.picture();
	}
}
