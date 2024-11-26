package org.badminton.api.filter;

import java.io.IOException;
import java.util.List;

import org.badminton.api.config.security.SecurityUtil;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.auth.jwt.JwtUtil;
import org.badminton.api.interfaces.member.dto.MemberResponse;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.member.entity.MemberAuthorization;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final ClubMemberReader clubMemberReader;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String accessToken = jwtUtil.extractAccessTokenFromCookie(request);
		String oauthToken = jwtUtil.extractOauthTokenFromCookie(request);
		try {
			if (jwtUtil.refreshAccessToken(accessToken, response) && jwtUtil.isAliveOauthToken(oauthToken)) {

				String memberToken = jwtUtil.getMemberToken(accessToken);
				String registrationId = jwtUtil.getRegistrationId(oauthToken);
				String oAuthAccessToken = jwtUtil.getOAuthToken(oauthToken);

				processAuthenticationWithMemberToken(memberToken, registrationId, oAuthAccessToken);
				filterChain.doFilter(request, response);
				return;
			}
		} catch (ExpiredJwtException expiredJwtException) {
			String memberToken = expiredJwtException.getClaims().get("memberToken", String.class);
			processAuthenticationWithMemberToken(memberToken, null, null);
		}
		filterChain.doFilter(request, response);

	}

	private void processAuthenticationWithMemberToken(String memberToken, String registrationId,
		String oAuthAccessToken) {
		List<ClubMember> clubMemberEntities = clubMemberReader.getActiveClubMembersByMemberToken(memberToken);
		MemberResponse memberResponse = new MemberResponse(memberToken,
			MemberAuthorization.AUTHORIZATION_USER.toString(),
			null, null, null, null);

		CustomOAuth2Member customOAuth2Member = new CustomOAuth2Member(
			memberResponse, registrationId, oAuthAccessToken);

		clubMemberEntities.stream()
			.filter(clubMember -> !clubMember.getClub().isClubDeleted())
			.forEach(clubMember -> customOAuth2Member.addClubRole(
				clubMember.getClub().getClubToken(),
				clubMember.getRole().name()
			));

		Authentication authToken = new UsernamePasswordAuthenticationToken(
			customOAuth2Member,
			null,
			customOAuth2Member.getAuthorities()
		);

		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return SecurityUtil.isPublicPath(request);
	}
}