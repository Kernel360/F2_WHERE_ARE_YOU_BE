package org.badminton.domain.common.exception.member;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class MemberAlreadyExistInClubException extends BadmintonException {

	public MemberAlreadyExistInClubException(String memberToken, String clubToken) {
		super(ErrorCode.MEMBER_ALREADY_JOINED_CLUB,
			"[회원 아이디 : " + memberToken + " 동호회 아이디 : " + clubToken + " 동호회 역할 : " + "]");
	}

	public MemberAlreadyExistInClubException(String memberToken, String clubToken, Exception e) {
		super(ErrorCode.MEMBER_ALREADY_JOINED_CLUB,
			"[회원 아이디 : " + memberToken + " 동호회 아이디 : " + clubToken + "]", e);
	}

}
