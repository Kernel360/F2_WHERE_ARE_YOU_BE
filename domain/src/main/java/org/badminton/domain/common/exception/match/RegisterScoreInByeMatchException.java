package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class RegisterScoreInByeMatchException extends BadmintonException {

	public RegisterScoreInByeMatchException(Long matchId, MatchType matchType) {
		super(ErrorCode.BYE_MATCH_ACTION_NOT_ALLOWED, "[매치 아이디 : " + matchId + "매치 유형: " + matchType + "]");
	}

	public RegisterScoreInByeMatchException(Long matchId, MatchType matchType, Exception e) {
		super(ErrorCode.BYE_MATCH_ACTION_NOT_ALLOWED, "[매치 아이디 : " + matchId + "매치 유형: " + matchType + "]", e);
	}
}
