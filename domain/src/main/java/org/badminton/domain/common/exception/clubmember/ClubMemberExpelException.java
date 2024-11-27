package org.badminton.domain.common.exception.clubmember;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class ClubMemberExpelException extends BadmintonException {
	public ClubMemberExpelException(String memberToken, String clubToken) {
		super(ErrorCode.CLUB_MEMBER_EXPEL_EXCEPTION, "[동호회 토큰: " + clubToken + ", 회원 토큰: " + memberToken + " ]");
	}

	public ClubMemberExpelException(String memberToken, String clubToken, Exception e) {
		super(ErrorCode.CLUB_MEMBER_EXPEL_EXCEPTION, "[동호회 토큰: " + clubToken + ", 회원 토큰: " + memberToken + " ]", e);
	}
}
