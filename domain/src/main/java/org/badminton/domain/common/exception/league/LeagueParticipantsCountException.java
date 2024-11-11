package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueParticipantsCountException extends BadmintonException {
	public LeagueParticipantsCountException() {
		super(ErrorCode.LEAGUE_PARTICIPANT_POWER_OF_TWO);
	}

	public LeagueParticipantsCountException(Exception e) {
		super(ErrorCode.LEAGUE_PARTICIPANT_POWER_OF_TWO, e);
	}
}
