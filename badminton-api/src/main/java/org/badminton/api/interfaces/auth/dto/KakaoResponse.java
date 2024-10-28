package org.badminton.api.interfaces.auth.dto;

import java.util.Map;

public class KakaoResponse implements OAuthResponse {

	private final KakaoAttributeDto attributes;

	public KakaoResponse(Map<String, Object> attributes) {
		this.attributes = KakaoAttributeDto.fromAttributes(attributes);
	}

	@Override
	public String getProvider() {
		return attributes.providerName();
	}

	@Override
	public String getProviderId() {
		return attributes.id();
	}

	@Override
	public String getEmail() {
		return attributes.kakaoAccountDto().email();
	}

	@Override
	public String getName() {
		return attributes.kakaoAccountDto().profile().nickname();
	}

	@Override
	public String getProfileImage() {
		return attributes.kakaoAccountDto().profile().profileImageUrl();
	}

}
