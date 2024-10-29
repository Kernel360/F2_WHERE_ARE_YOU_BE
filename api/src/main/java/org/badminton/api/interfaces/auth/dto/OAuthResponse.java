package org.badminton.api.interfaces.auth.dto;

public interface OAuthResponse {

	String getProvider(); // oAuth 제공자

	String getProviderId(); // 제공자에서 발급해주는 아이디

	String getEmail(); // 이메일

	String getName(); // 설정한 이름

	String getProfileImage();
}
