package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.league.enums.LeagueStatus;

public class InvalidLeagueStatusToGenerateBracketException extends BadmintonException {
	public InvalidLeagueStatusToGenerateBracketException(Long leagueId, LeagueStatus leagueStatus) {
		super(ErrorCode.INVALID_LEAGUE_STATUS_TO_GENERATE_BRACKET,
			"[경기 아이디: " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + " ]");
	}

	public InvalidLeagueStatusToGenerateBracketException(Long leagueId, LeagueStatus leagueStatus, Exception e) {
		super(ErrorCode.INVALID_LEAGUE_STATUS_TO_GENERATE_BRACKET,
			"[경기 아이디: " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + " ]", e);
	}
}
