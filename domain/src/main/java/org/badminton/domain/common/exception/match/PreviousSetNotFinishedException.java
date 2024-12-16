package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class PreviousSetNotFinishedException extends BadmintonException {

	public PreviousSetNotFinishedException(int setNumber) {
		super(ErrorCode.SET_NOT_FINISHED, "[세트 번호 : " + setNumber + "]");
	}

	public PreviousSetNotFinishedException(int setNumber, Exception e) {
		super(ErrorCode.SET_NOT_FINISHED, "[세트 번호 : " + setNumber + "]", e);
	}
}
