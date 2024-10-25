package org.badminton.domain.common.exception.club;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class ClubNotExistException extends BadmintonException {

	public ClubNotExistException(String clubId) {
		super(ErrorCode.CLUB_NOT_EXIST, "[동호회 아이디 : " + clubId + "]");
	}

	public ClubNotExistException(Long clubId, Exception e) {
		super(ErrorCode.CLUB_NOT_EXIST, "[동호회 아이디 : " + clubId + "]", e);
	}
}
