package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class ParticipationLimitReachedException extends BadmintonException {
	public ParticipationLimitReachedException(Long leagueId) {
		super(ErrorCode.LEAGUE_RECRUITING_ALREADY_COMPLETED, "[경기 일정 아이디: " + leagueId + " ]");
	}

	public ParticipationLimitReachedException(Long leagueId, Exception exception) {
		super(ErrorCode.LEAGUE_RECRUITING_ALREADY_COMPLETED, "[경기 일정 아이디: " + leagueId + " ]", exception);
	}
}
