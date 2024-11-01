package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class PlayerLimitCountMustBeMoreThanFourException extends BadmintonException {

	public PlayerLimitCountMustBeMoreThanFourException(int playerLimitCount) {
		super(ErrorCode.PLAYER_LIMIT_COUNT_MUST_BE_MORE_THAN_FOUR, "[최대 참여 인원 : " + playerLimitCount + "]");
	}

	public PlayerLimitCountMustBeMoreThanFourException(int playerLimitCount, Exception e) {
		super(ErrorCode.PLAYER_LIMIT_COUNT_MUST_BE_MORE_THAN_FOUR, "[최대 참여 인원 : " + playerLimitCount + "]", e);
	}
}
