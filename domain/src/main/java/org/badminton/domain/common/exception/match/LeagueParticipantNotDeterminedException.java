package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueParticipantNotDeterminedException extends BadmintonException {

	public LeagueParticipantNotDeterminedException(Long matchId) {
		super(ErrorCode.LEAGUE_PARTICIPANTS_NOT_DETERMINED, "[매치 번호 : " + matchId + "]");
	}

	public LeagueParticipantNotDeterminedException(Long matchId, Exception e) {
		super(ErrorCode.LEAGUE_PARTICIPANTS_NOT_DETERMINED, "[매치 번호 : " + matchId + "]", e);
	}
}
