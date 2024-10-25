package org.badminton.api.filter;

import java.io.IOException;
import java.util.List;

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
		String token = jwtUtil.extractAccessTokenFromCookie(request);

		if (token != null && jwtUtil.validateToken(token)) {
			String memberToken = jwtUtil.getMemberToken(token);
			List<ClubMember> clubMemberEntities = clubMemberReader.getClubMembersByMemberToken(memberToken);

			String oAuthAccessToken = jwtUtil.getOAuthToken(token);

			MemberResponse memberResponse = new MemberResponse(memberToken,
				MemberAuthorization.AUTHORIZATION_USER.toString(), null, null, null, null);
			CustomOAuth2Member customOAuth2Member = new CustomOAuth2Member(memberResponse,
				jwtUtil.getRegistrationId(token), oAuthAccessToken);

			for (ClubMember clubMember : clubMemberEntities) {
				if (!clubMember.getClub().isClubDeleted()) {
					customOAuth2Member.addClubRole(clubMember.getClub().getClubToken(), clubMember.getRole().name());
					break;
				}
			}

			Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2Member, null,
				customOAuth2Member.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authToken);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.equals("/") || path.equals("/groups") || path.startsWith("/oauth2") || path.startsWith("/login")
			|| path.startsWith("/api") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")
			;
	}

}
