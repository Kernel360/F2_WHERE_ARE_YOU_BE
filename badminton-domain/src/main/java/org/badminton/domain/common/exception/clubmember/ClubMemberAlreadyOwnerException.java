package org.badminton.domain.common.exception.clubmember;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class ClubMemberAlreadyOwnerException extends BadmintonException {

	public ClubMemberAlreadyOwnerException(String memberToken) {
		super(ErrorCode.CLUB_MEMBER_ALREADY_OWNER, "[동호회 회원 아이디: " + memberToken + "]");
	}

	public ClubMemberAlreadyOwnerException(Long clubMemberId, Exception e) {
		super(ErrorCode.CLUB_MEMBER_ALREADY_OWNER, "[동호회 회원 아이디: " + clubMemberId + "]");
	}
}
