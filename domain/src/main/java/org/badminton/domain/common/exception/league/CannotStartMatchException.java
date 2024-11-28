package org.badminton.domain.common.exception.league;

import java.time.LocalDateTime;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class CannotStartMatchException extends BadmintonException {

	public CannotStartMatchException(Long leagueId, LocalDateTime leagueAt) {
		super(ErrorCode.INVALID_TIME_TO_START_MATCH, "[경기 아이디 : " + leagueId + " 경기 시작 시간 : " + leagueAt + " ]");
	}

	public CannotStartMatchException(Long leagueId, LocalDateTime leagueAt, Exception e) {
		super(ErrorCode.INVALID_TIME_TO_START_MATCH, "[경기 아이디 : " + leagueId + " 경기 시작 시간 : " + leagueAt + " ]", e);
	}
}
