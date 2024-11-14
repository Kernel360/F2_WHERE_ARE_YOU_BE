package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class SetFinishedException extends BadmintonException {

	public SetFinishedException(int setNumber) {
		super(ErrorCode.SET_FINISHED, "[세트 번호 : " + setNumber + " ]");
	}

	public SetFinishedException(int setNumber, Exception e) {
		super(ErrorCode.SET_FINISHED, "[세트 번호 : " + setNumber + " ]", e);
	}
}
