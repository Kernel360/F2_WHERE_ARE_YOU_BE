package org.badminton.domain.domain.auth.service;

import org.badminton.domain.domain.member.info.MemberDeleteInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
	MemberDeleteInfo deleteMember(String accessToken, String registrationId);

	void logoutMember(String memberToken, HttpServletResponse response);

	String refreshAccessToken(HttpServletRequest request, HttpServletResponse response);
}
