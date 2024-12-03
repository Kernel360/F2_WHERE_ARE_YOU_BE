package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class ByeMatchException extends BadmintonException {

	public ByeMatchException(Long matchId) {
		super(ErrorCode.BYE_MATCH, "[매치 아이디 : " + matchId + "]");
	}

	public ByeMatchException(Long matchId, Exception e) {
		super(ErrorCode.BYE_MATCH, "[매치 아이디 : " + matchId + "]", e);
	}
}
