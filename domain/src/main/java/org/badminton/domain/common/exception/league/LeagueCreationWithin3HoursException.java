package org.badminton.domain.common.exception.league;

import java.time.LocalDateTime;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueCreationWithin3HoursException extends BadmintonException {
	public LeagueCreationWithin3HoursException(LocalDateTime localDateTime) {
		super(ErrorCode.LEAGUE_AT_LESS_THAN_THREE_HOUR_INTERVAL, "[경기 일정 : " + localDateTime + "]");
	}
}
