package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueParticipantsNotExistsException extends BadmintonException {

	public LeagueParticipantsNotExistsException(Long matchId) {
		super(ErrorCode.LEAGUE_PARTICIPANTS_NOT_EXISTS, "[매치 ID : " + matchId + "]");
	}

	public LeagueParticipantsNotExistsException(Long matchId, Exception e) {
		super(ErrorCode.LEAGUE_PARTICIPANTS_NOT_EXISTS, "[매치 ID : " + matchId + "]", e);
	}
}
