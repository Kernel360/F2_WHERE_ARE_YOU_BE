package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class RoundNotFinishedException extends BadmintonException {

	public RoundNotFinishedException(int roundNumber) {
		super(ErrorCode.ROUND_NOT_FINISHED, "[라운드 번호 : " + roundNumber + "]");
	}

	public RoundNotFinishedException(int roundNumber, Exception e) {
		super(ErrorCode.ROUND_NOT_FINISHED, "[라운드 번호 : " + roundNumber + "]", e);
	}
}
