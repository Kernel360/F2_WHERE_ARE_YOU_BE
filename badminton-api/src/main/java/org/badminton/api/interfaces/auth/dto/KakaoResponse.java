package org.badminton.api.interfaces.auth.dto;

import java.util.Map;

public class KakaoResponse implements OAuthResponse {

	private final Map<String, Object> attributes;
	private final Map<String, Object> kakaoAccount;
	private final Map<String, Object> profile;

	public KakaoResponse(Map<String, Object> attributes) {
		this.attributes = attributes;
		this.kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		this.profile = (Map<String, Object>)kakaoAccount.get("profile");
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getProviderId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getEmail() {
		return kakaoAccount.get("email").toString();
	}

	@Override
	public String getName() {
		Map<String, Object> profile = (Map<String, Object>)kakaoAccount.get("profile");
		return profile.get("nickname").toString();
	}

	@Override
	public String getProfileImage() {
		return profile.get("profile_image_url").toString();
	}

}
