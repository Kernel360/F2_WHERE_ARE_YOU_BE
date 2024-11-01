package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class PlayerLimitCountDecreasedNotAllowedException extends BadmintonException {
	public PlayerLimitCountDecreasedNotAllowedException(int playerLimitcount, int modifiedLimitCount) {
		super(ErrorCode.PLAYER_LIMIT_COUNT_DECREASED_NOT_ALLOWED,
			"[기존 참여 제한 인원 : " + playerLimitcount + " 변경하려는 참여 제한 인원 : " + modifiedLimitCount);
	}
}
