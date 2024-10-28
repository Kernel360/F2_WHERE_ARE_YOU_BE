package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueNotExistException extends BadmintonException {

	public LeagueNotExistException(Long leagueId) {
		super(ErrorCode.LEAGUE_NOT_EXIST, "[경기 일정 아이디 : " + leagueId + "]");
	}

	public LeagueNotExistException(Long leagueId, Exception e) {
		super(ErrorCode.LEAGUE_NOT_EXIST, "[경기 일정 아이디 : " + leagueId + "]", e);
	}

	public LeagueNotExistException(String clubToken, Long leagueId) {
		super(ErrorCode.LEAGUE_NOT_EXIST, "[동호회 아이디 : " + clubToken + " 경기 일정 아이디 : " + leagueId + "]");
	}

	public LeagueNotExistException(String clubToken, Long leagueId, Exception e) {
		super(ErrorCode.LEAGUE_NOT_EXIST, "[동호회 아이디 : " + clubToken + " 경기 일정 아이디 : " + leagueId + "]", e);
	}
}
