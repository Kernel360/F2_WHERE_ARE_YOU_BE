package org.badminton.domain.common.exception.league;

import java.time.LocalDateTime;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class InvalidDateTimeToParticipateInLeagueException extends BadmintonException {
	public InvalidDateTimeToParticipateInLeagueException(Long leagueId, LocalDateTime recruitingClosedAt) {
		super(ErrorCode.INVALID_TIME_TO_PARTICIPATE_IN_LEAGUE,
			"[경기 아이디 : " + leagueId + " 경기 모집 마감 시간 : " + recruitingClosedAt + " ]");
	}

	public InvalidDateTimeToParticipateInLeagueException(Long leagueId, LocalDateTime recruitingClosedAt,
		Exception e) {
		super(ErrorCode.INVALID_TIME_TO_PARTICIPATE_IN_LEAGUE,
			"[경기 아이디 : " + leagueId + " 경기 모집 마감 시간 : " + recruitingClosedAt + " ]", e);
	}
}
