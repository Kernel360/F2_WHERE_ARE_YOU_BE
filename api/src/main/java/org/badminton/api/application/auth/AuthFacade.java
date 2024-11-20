package org.badminton.api.application.auth;

import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.domain.domain.auth.info.MemberDeleteInfo;
import org.badminton.domain.domain.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacade {

	private final AuthService authService;
	@Value("${custom.server.domain}")
	private String domain;

	public MemberDeleteInfo deleteMember(
		CustomOAuth2Member member,
		HttpServletResponse response) {

		String memberToken = member.getMemberToken();
		String oauthToken = member.getOAuthAccessToken();
		String registrationId = member.getRegistrationId();

		MemberDeleteInfo memberDeleteInfo = authService.deleteMember(memberToken, oauthToken, registrationId);

		clearAccessCookie(response);
		clearOauthCookie(response);
		response.setHeader("Authorization", "");

		return memberDeleteInfo;

	}

	public void logoutMember(String memberToken, HttpServletResponse response) {

		authService.logoutMember(memberToken, response);
		clearAccessCookie(response);
		clearOauthCookie(response);

	}

	private void clearAccessCookie(HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from("access_token", "")
			.httpOnly(true)
			.secure(true)
			.sameSite("None")
			.domain(domain)
			.path("/")
			.maxAge(0)  // 만료 시간을 0으로 설정하여 쿠키 삭제
			.build();
		response.addHeader("Set-Cookie", cookie.toString());
	}

	private void clearOauthCookie(HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from("oauth_token", "")
			.httpOnly(true)
			.secure(true)
			.sameSite("None")
			.domain(domain)
			.path("/")
			.maxAge(0)  // 만료 시간을 0으로 설정하여 쿠키 삭제
			.build();
		response.addHeader("Set-Cookie", cookie.toString());
	}
}
