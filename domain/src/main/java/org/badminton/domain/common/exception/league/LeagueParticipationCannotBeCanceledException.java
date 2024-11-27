package org.badminton.domain.common.exception.league;

import java.time.LocalDateTime;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.league.enums.LeagueStatus;

public class LeagueParticipationCannotBeCanceledException extends BadmintonException {
	public LeagueParticipationCannotBeCanceledException(Long leagueId, LeagueStatus leagueStatus) {
		super(ErrorCode.INVALID_LEAGUE_STATUS_TO_CANCEL_LEAGUE_PARTICIPATION,
			"[경기 일정 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + "]");
	}

	public LeagueParticipationCannotBeCanceledException(Long leagueId, LocalDateTime recruitingClosedAt) {
		super(ErrorCode.INVALID_TIME_TO_CANCEL_LEAGUE_PARTICIPATION,
			"[경기 일정 아이디 : " + leagueId + " 경기 모집 마감 날짜 : " + recruitingClosedAt + "]");
	}

	public LeagueParticipationCannotBeCanceledException(Long leagueId, LeagueStatus leagueStatus, Exception e) {
		super(ErrorCode.INVALID_LEAGUE_STATUS_TO_CANCEL_LEAGUE_PARTICIPATION,
			"[경기 일정 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + "]", e);
	}
}
