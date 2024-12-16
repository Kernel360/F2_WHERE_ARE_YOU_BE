package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class CannotStartMatchException extends BadmintonException {
	public CannotStartMatchException(Long matchId, MatchStatus matchStatus) {
		super(ErrorCode.CANNOT_START_MATCH, "[매치 아이디: " + matchId + " 매치 상태 : " + matchStatus + "]");
	}

	public CannotStartMatchException(Long matchId, MatchStatus matchStatus, Exception e) {
		super(ErrorCode.CANNOT_START_MATCH, "[매치 아이디: " + matchId + " 매치 상태 : " + matchStatus + "]", e);
	}
}
