package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class PlayerLimitCountMustBeMultipleWhenDoublesMatch extends BadmintonException {
	public PlayerLimitCountMustBeMultipleWhenDoublesMatch(int playerLimitCount, MatchType matchType) {
		super(ErrorCode.PLAYER_LIMIT_COUNT_MUST_BE_MULTIPLE_WHEN_DOUBLES_MATCH,
			"[최대 참여 인원 : " + playerLimitCount + " 경기 타입 : " + matchType + "]");
	}

	public PlayerLimitCountMustBeMultipleWhenDoublesMatch(int playerLimitCount, MatchType matchType, Exception e) {
		super(ErrorCode.PLAYER_LIMIT_COUNT_MUST_BE_MULTIPLE_WHEN_DOUBLES_MATCH,
			"[최대 참여 인원 : " + playerLimitCount + " 경기 타입 : " + matchType + "]", e);
	}
}

