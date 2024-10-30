package org.badminton.api.interfaces.auth.successhandler;

import java.io.IOException;
import java.util.List;

import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.auth.jwt.JwtUtil;
import org.badminton.domain.domain.member.MemberReader;
import org.badminton.domain.domain.member.MemberStore;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtUtil jwtUtil;
	private final MemberReader memberReader;
	private final MemberStore memberStore;

	@Value("${custom.server.front}")
	private String frontUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		log.info("CustomSuccessHandler onAuthenticationSuccess");

		CustomOAuth2Member customUserDetails = (CustomOAuth2Member)authentication.getPrincipal(); // 인증된 사용자 객체 가져오기

		String memberToken = customUserDetails.getMemberToken();
		Member member = memberReader.getMember(memberToken);

		member.updateLastConnection();

		String registrationId = customUserDetails.getRegistrationId();

		String oAuthAccessToken = customUserDetails.getOAuthAccessToken();

		List<String> roles = customUserDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.toList();

		log.info("roles: {}", roles);

		String accessToken = jwtUtil.createAccessToken(memberToken, roles);

		String oAuthToken = jwtUtil.createOauthToken(registrationId, oAuthAccessToken);

		String refreshToken = jwtUtil.createRefreshToken(memberToken);

		log.info("Extracted from JWT - registrationId: {}, oAuthAccessToken: {}",
			jwtUtil.getRegistrationId(oAuthToken), jwtUtil.getOAuthToken(oAuthToken));

		member.updateRefreshToken(refreshToken);

		memberStore.store(member);

		clearSession(request, response);

		jwtUtil.setAccessTokenCookie(response, accessToken);
		jwtUtil.setOauthTokenCookie(response, oAuthToken);

		response.sendRedirect(frontUrl);

	}

	private void clearSession(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		ResponseCookie jSessionIdCookie = ResponseCookie.from("JSESSIONID", "")
			.httpOnly(true)
			.secure(true)
			.path("/")
			.maxAge(0)
			.build();
		response.addHeader("Set-Cookie", jSessionIdCookie.toString());
	}
}


