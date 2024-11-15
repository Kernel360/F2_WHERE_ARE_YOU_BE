package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class AlreadyWinnerDeterminedException extends BadmintonException {

	public AlreadyWinnerDeterminedException(Long matchId) {
		super(ErrorCode.ALREADY_WINNER_DETERMINED, "[매치 아이디 : " + matchId + "]");
	}

	public AlreadyWinnerDeterminedException(Long matchId, Exception e) {
		super(ErrorCode.ALREADY_WINNER_DETERMINED, "[매치 아이디 : " + matchId + "]", e);
	}
}
