package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueAlreadyCanceledException extends BadmintonException {
	public LeagueAlreadyCanceledException(Long leagueId) {
		super(ErrorCode.LEAGUE_ALREADY_CANCELED, "[리그 아이디: " + leagueId + "]");
	}

	public LeagueAlreadyCanceledException(Long leagueId, Exception exception) {
		super(ErrorCode.LEAGUE_ALREADY_CANCELED, "[리그 아이디: " + leagueId + "]", exception);
	}

}
