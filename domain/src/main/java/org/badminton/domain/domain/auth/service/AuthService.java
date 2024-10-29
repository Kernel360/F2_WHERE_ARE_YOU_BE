package org.badminton.domain.domain.auth.service;

import org.badminton.domain.domain.auth.info.MemberDeleteInfo;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
	MemberDeleteInfo deleteMember(String memberToken, String accessToken, String registrationId);

	void logoutMember(String memberToken, HttpServletResponse response);
}
