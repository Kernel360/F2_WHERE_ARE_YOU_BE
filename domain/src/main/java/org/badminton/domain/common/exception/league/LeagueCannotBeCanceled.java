package org.badminton.domain.common.exception.league;

import java.time.LocalDateTime;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueCannotBeCanceled extends BadmintonException {

	public LeagueCannotBeCanceled(Long leagueId, LocalDateTime leagueAt) {
		super(ErrorCode.INVALID_TIME_TO_CANCEL_LEAGUE, "[경기 아이디 : " + leagueId + " 경기 시간 : " + leagueAt + "]");
	}

	public LeagueCannotBeCanceled(Long leagueId, LocalDateTime leagueAt, Exception e) {
		super(ErrorCode.INVALID_TIME_TO_CANCEL_LEAGUE, "[경기 아이디 : " + leagueId + " 경기 시간 : " + leagueAt + "]", e);
	}
}
