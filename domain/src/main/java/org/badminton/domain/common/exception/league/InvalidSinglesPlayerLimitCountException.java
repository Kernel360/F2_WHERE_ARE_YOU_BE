package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class InvalidSinglesPlayerLimitCountException extends BadmintonException {
	public InvalidSinglesPlayerLimitCountException(int playerLimitCount, MatchType matchType) {
		super(ErrorCode.PLAYER_LIMIT_COUNT_MUST_BE_MORE_THAN_TWO_WHEN_SINGLES,
			"[최대 참여 인원 : " + playerLimitCount + " 경기 타입 : " + matchType + "]");
	}

	public InvalidSinglesPlayerLimitCountException(int playerLimitCount, MatchType matchType, Exception e) {
		super(ErrorCode.PLAYER_LIMIT_COUNT_MUST_BE_MORE_THAN_TWO_WHEN_SINGLES,
			"[최대 참여 인원 : " + playerLimitCount + " 경기 타입 : " + matchType + "]", e);
	}
}

