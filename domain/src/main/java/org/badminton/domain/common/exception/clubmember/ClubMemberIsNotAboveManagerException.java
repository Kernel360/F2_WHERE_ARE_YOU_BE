package org.badminton.domain.common.exception.clubmember;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.clubmember.entity.ClubMember;

public class ClubMemberIsNotAboveManagerException extends BadmintonException {
	public ClubMemberIsNotAboveManagerException(String memberToken, String clubToken,
		ClubMember.ClubMemberRole currentRole) {
		super(ErrorCode.CLUB_MEMBER_IS_NOT_ABOVE_MANAGER,
			"[회원 토큰 : " + memberToken + " 동호회 토큰 : " + clubToken + " 동호회 권한 " + currentRole + " ]");
	}

	public ClubMemberIsNotAboveManagerException(String memberToken, String clubToken,
		ClubMember.ClubMemberRole currentRole, Exception e) {
		super(ErrorCode.CLUB_MEMBER_IS_NOT_ABOVE_MANAGER,
			"[회원 토큰 : " + memberToken + " 동호회 토큰 : " + clubToken + " 동호회 권한 " + currentRole + " ]", e);
	}
}
