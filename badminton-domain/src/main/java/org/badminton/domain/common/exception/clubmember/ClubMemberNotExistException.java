package org.badminton.domain.common.exception.clubmember;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class ClubMemberNotExistException extends BadmintonException {

	public ClubMemberNotExistException(String clubToken, String memberToken) {
		super(ErrorCode.CLUB_MEMBER_NOT_EXIST, "[동호회 아이디 : " + clubToken + " 회원 아이디 : " + memberToken + "]");
	}

	public ClubMemberNotExistException(String clubToken, String memberToken, Exception e) {
		super(ErrorCode.CLUB_MEMBER_NOT_EXIST, "[동호회 아이디 : " + clubToken + " 회원 아이디 : " + memberToken + "]", e);
	}

	public ClubMemberNotExistException(Long clubMemberId) {
		super(ErrorCode.CLUB_MEMBER_NOT_EXIST, "[동호회 회원 아이디 : " + clubMemberId + "]");
	}

	public ClubMemberNotExistException(Long clubMemberId, Exception e) {
		super(ErrorCode.CLUB_MEMBER_NOT_EXIST, "[동호회 회원 아이디 : " + clubMemberId + "]", e);
	}

}
