package org.badminton.domain.common.exception.clubmember;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class ClubOwnerCannotWithdraw extends BadmintonException {

	public ClubOwnerCannotWithdraw(Long clubMemberId) {
		super(ErrorCode.CLUB_OWNER_CANT_WITHDRAW, "[동호회 회원 아이디: " + clubMemberId + "]");
	}

	public ClubOwnerCannotWithdraw(Long clubMemberId, Exception e) {
		super(ErrorCode.CLUB_OWNER_CANT_WITHDRAW, "[동호회 회원 아이디: " + clubMemberId + "]", e);
	}
}
