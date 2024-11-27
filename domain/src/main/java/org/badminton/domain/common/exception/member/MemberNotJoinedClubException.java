package org.badminton.domain.common.exception.member;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class MemberNotJoinedClubException extends BadmintonException {

	public MemberNotJoinedClubException(String memberToken, String clubToken) {
		super(ErrorCode.MEMBER_NOT_JOINED_CLUB, "[회원 토큰 : " + memberToken + "동호회 토큰: " + clubToken + " ]");
	}

	public MemberNotJoinedClubException(String memberToken, String clubToken, Exception e) {
		super(ErrorCode.MEMBER_NOT_JOINED_CLUB, "[회원 토큰 : " + memberToken + "동호회 토큰: " + clubToken + " ]", e);
	}

}
