package org.badminton.domain.common.exception.clubmember;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.clubmember.entity.ClubMember;

public class ClubMemberIsNotOwnerException extends BadmintonException {
	public ClubMemberIsNotOwnerException(String memberToken, String clubToken, ClubMember.ClubMemberRole currentRole) {
		super(ErrorCode.CLUB_MEMBER_IS_NOT_OWNER,
			"[회원 토큰 : " + memberToken + " 동호회 토큰 : " + clubToken + " 현재 회원 권한 : " + currentRole + " ]");
	}

	public ClubMemberIsNotOwnerException(ErrorCode errorCode, String errorDetails) {
		super(errorCode, errorDetails);
	}

}
