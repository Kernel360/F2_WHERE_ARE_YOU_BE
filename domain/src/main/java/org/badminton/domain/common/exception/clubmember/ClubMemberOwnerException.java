package org.badminton.domain.common.exception.clubmember;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class ClubMemberOwnerException extends BadmintonException {

	public ClubMemberOwnerException(String clubToken) {
		super(ErrorCode.CLUB_MEMBER_OWNER_PROTECT, "[" + clubToken + "의 회장입니다.]");
	}

	public ClubMemberOwnerException(String clubToken, Exception e) {
		super(ErrorCode.CLUB_MEMBER_OWNER_PROTECT, "[" + clubToken + "의 회장입니다.]");
	}
}
