package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.league.enums.LeagueStatus;

public class LeagueRecruitingMustBeCompletedWhenBracketGenerationException extends BadmintonException {

	public LeagueRecruitingMustBeCompletedWhenBracketGenerationException(Long leagueId, LeagueStatus leagueStatus) {
		super(ErrorCode.LEAGUE_RECRUITING_MUST_BE_COMPLETED_WHEN_BRACKET_GENERATION,
			"[경기 일정 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus);
	}

	public LeagueRecruitingMustBeCompletedWhenBracketGenerationException(Long leagueId, LeagueStatus leagueStatus,
		Exception e) {
		super(ErrorCode.LEAGUE_RECRUITING_MUST_BE_COMPLETED_WHEN_BRACKET_GENERATION,
			"[경기 일정 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus, e);
	}
}
