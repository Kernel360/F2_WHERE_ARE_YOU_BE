package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class InvalidDoublesPlayerLimitCountException extends BadmintonException {

	public InvalidDoublesPlayerLimitCountException(int playerLimitCount) {
		super(ErrorCode.PLAYER_LIMIT_COUNT_MUST_BE_MORE_THAN_FOUR_WHEN_DOUBLES,
			"[최대 참여 인원 : " + playerLimitCount + "]");
	}

	public InvalidDoublesPlayerLimitCountException(int playerLimitCount, MatchType matchType) {
		super(ErrorCode.PLAYER_LIMIT_COUNT_MUST_BE_MULTIPLE_WHEN_DOUBLES,
			"[최대 참여 인원 : " + playerLimitCount + " 경기 타입 : " + matchType + "]");
	}

	public InvalidDoublesPlayerLimitCountException(int playerLimitCount, Exception e) {
		super(ErrorCode.PLAYER_LIMIT_COUNT_MUST_BE_MORE_THAN_FOUR_WHEN_DOUBLES, "[최대 참여 인원 : " + playerLimitCount + "]",
			e);
	}
}
