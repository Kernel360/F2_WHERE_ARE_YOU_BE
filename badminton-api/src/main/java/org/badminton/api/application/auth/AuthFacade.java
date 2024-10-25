// package org.badminton.api.application.auth;
//
// import java.util.List;
//
// import org.badminton.api.interfaces.oauth.dto.CustomOAuth2Member;
// import org.badminton.api.interfaces.oauth.jwt.JwtUtil;
// import org.badminton.domain.domain.auth.service.AuthService;
// import org.badminton.domain.domain.member.MemberReader;
// import org.badminton.domain.domain.member.MemberStore;
// import org.badminton.domain.domain.member.entity.Member;
// import org.badminton.domain.domain.member.info.MemberDeleteInfo;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.ResponseCookie;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.stereotype.Service;
//
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Service
// @RequiredArgsConstructor
// public class AuthFacade {
//
// 	private final AuthService authService;
// 	private final MemberStore memberStore;
// 	private final MemberReader memberReader;
// 	private final JwtUtil jwtUtil;
// 	@Value("${spring.security.oauth2.revoke-url.naver}")
// 	private String naverRevokeUrl;
// 	@Value("${spring.security.oauth2.revoke-url.kakao}")
// 	private String kakaoRevokeUrl;
// 	@Value("${spring.security.oauth2.revoke-url.google}")
// 	private String googleRevokeUrl;
// 	@Value("${NAVER_CLIENT_ID}")
// 	private String naverClientId;
// 	@Value("${NAVER_CLIENT_SECRET}")
// 	private String naverClientSecret;
// 	@Value("${custom.server.domain}")
// 	private String domain;
//
// 	public MemberDeleteInfo deleteMember(@AuthenticationPrincipal CustomOAuth2Member member,
// 		HttpServletResponse response) {
//
// 		String memberToken = member.getMemberToken();
// 		String accessToken = member.getOAuthAccessToken();
// 		String registrationId = member.getRegistrationId();
//
// 		authService.deleteMember(memberToken, accessToken, registrationId);
//
// 		clearRefreshCookie(response);
// 		clearAccessCookie(response);
// 		response.setHeader("Authorization", "");
//
// 		unLinkOAuth(registrationId, accessToken);
//
// 		String memberToken = member.getMemberToken();
// 		MemberDeleteInfo memberDeleteInfo = changeIsDeleted(memberToken);
//
// 		return memberDeleteInfo;
//
// 	}
//
// 	private MemberDeleteInfo changeIsDeleted(String memberToken) {
// 		Member member = memberReader.getMember(memberToken);
// 		member.doWithdrawal();
// 		memberStore.store(member);
// 		log.info("Member marked as deleted: {}", memberToken);
// 		return MemberDeleteInfo.fromMemberDeleteInfo(member);
// 	}
//
// 	public void logoutMember(String memberToken, HttpServletResponse response) {
// 		Member member = findMemberByMemberId(memberToken);
// 		member.updateRefreshToken(null);
// 		memberStore.store(member);
// 		response.setHeader("Authorization", "");
//
// 		clearRefreshCookie(response);
// 		clearAccessCookie(response);
//
// 		log.info("Logged out member: {}", memberToken);
// 	}
//
// 	public String refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
// 		String refreshToken = jwtUtil.extractRefreshTokenFromCookie(request);
// 		if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
// 			String memberToken = jwtUtil.getMemberToken(refreshToken);
// 			List<String> roles = jwtUtil.getRoles(refreshToken);
// 			String registrationId = jwtUtil.getRegistrationId(refreshToken);
// 			String oAuthAccessToken = jwtUtil.getOAuthToken(refreshToken);
//
// 			String newAccessToken = jwtUtil.createAccessToken(memberToken, roles, registrationId, oAuthAccessToken);
// 			jwtUtil.setAccessTokenHeader(response, newAccessToken);
// 			return newAccessToken;
// 		}
// 		return null;
// 	}
//
// 	private Member findMemberByMemberId(String memberToken) {
// 		return memberReader.getMember(memberToken);
// 	}
//
// 	private void clearRefreshCookie(HttpServletResponse response) {
// 		ResponseCookie cookie = ResponseCookie.from("refresh_token", "")
// 			.httpOnly(true)
// 			.secure(true)
// 			.sameSite("None")
// 			.domain(domain)
// 			.path("/")
// 			.maxAge(0)  // 만료 시간을 0으로 설정하여 쿠키 삭제
// 			.build();
// 		response.addHeader("Set-Cookie", cookie.toString());
// 	}
//
// 	private void clearAccessCookie(HttpServletResponse response) {
// 		ResponseCookie cookie = ResponseCookie.from("access_token", "")
// 			.httpOnly(true)
// 			.secure(true)
// 			.sameSite("None")
// 			.domain(domain)
// 			.path("/")
// 			.maxAge(0)  // 만료 시간을 0으로 설정하여 쿠키 삭제
// 			.build();
// 		response.addHeader("Set-Cookie", cookie.toString());
// 	}
//
// }
