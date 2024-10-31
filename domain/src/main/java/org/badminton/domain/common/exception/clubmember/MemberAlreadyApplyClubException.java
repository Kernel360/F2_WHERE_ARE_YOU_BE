package org.badminton.domain.common.exception.clubmember;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class MemberAlreadyApplyClubException extends BadmintonException {

	public MemberAlreadyApplyClubException(String memberToken, String clubToken) {
		super(ErrorCode.MEMBER_ALREADY_APPLY_CLUB, "[회원 아이디: " + memberToken + "동호회 아이디: " + clubToken + "]");
	}

	public MemberAlreadyApplyClubException(String memberToken, String clubToken, Exception e) {
		super(ErrorCode.MEMBER_ALREADY_APPLY_CLUB, "[회원 아이디: " + memberToken + "동호회 아이디: " + clubToken + "]" + e);
	}
}
