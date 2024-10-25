package org.badminton.api.filter;

import java.io.IOException;

import org.badminton.api.config.security.SecurityUtil;
import org.badminton.api.interfaces.oauth.dto.CustomOAuth2Member;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
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
public class ClubMembershipFilter extends OncePerRequestFilter {

	private final ClubMemberReader clubMemberReader;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		return !path.startsWith("/v1/clubs") || SecurityUtil.isPublicPath(request) || path.startsWith("/v1/members");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String clubId = SecurityUtil.extractClubIdFromRequest(request);

		if (clubId != null && !isClubMember(clubId)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not a member of this club");
			return;
		}

		filterChain.doFilter(request, response);
	}

	private boolean isClubMember(String clubId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !(auth.getPrincipal() instanceof CustomOAuth2Member)) {
			return false;
		}
		CustomOAuth2Member member = (CustomOAuth2Member)auth.getPrincipal();
		return clubMemberReader.existsMemberInClub(member.getMemberToken(), clubId);
	}
}


