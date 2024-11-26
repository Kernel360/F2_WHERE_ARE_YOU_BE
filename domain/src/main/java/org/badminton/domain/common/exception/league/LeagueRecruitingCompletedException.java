package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.league.enums.LeagueStatus;

public class LeagueRecruitingCompletedException extends BadmintonException {
	public LeagueRecruitingCompletedException(Long leagueId, LeagueStatus leagueStatus, int playerLimitCount) {
		super(ErrorCode.LEAGUE_RECRUITING_ALREADY_COMPLETED,
			"[경기 아이디 : " + leagueId + " 경기 상태 " + leagueStatus.getDescription() +
				" 모집 인원 : " + playerLimitCount + "]");
	}

	public LeagueRecruitingCompletedException(Long leagueId, LeagueStatus leagueStatus, int playerLimitCount,
		Exception e) {
		super(ErrorCode.LEAGUE_RECRUITING_ALREADY_COMPLETED,
			"[경기 아이디 : " + leagueId + " 경기 상태 " + leagueStatus.getDescription() +
				" 모집 인원 : " + playerLimitCount + "]", e);
	}
}
