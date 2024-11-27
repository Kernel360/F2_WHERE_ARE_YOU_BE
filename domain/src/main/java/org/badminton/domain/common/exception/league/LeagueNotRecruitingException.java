package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.league.enums.LeagueStatus;

public class LeagueNotRecruitingException extends BadmintonException {
	public LeagueNotRecruitingException(Long leagueId, LeagueStatus leagueStatus) {
		super(ErrorCode.LEAGUE_NOT_RECRUITING,
			"[경기 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + " ]");
	}

	public LeagueNotRecruitingException(Long leagueId, LeagueStatus leagueStatus, Exception e) {
		super(ErrorCode.LEAGUE_NOT_RECRUITING,
			"[경기 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + " ]", e);
	}
}
