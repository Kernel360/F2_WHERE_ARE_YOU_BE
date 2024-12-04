package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueParticipationNotExistInMatchException extends BadmintonException {

	public LeagueParticipationNotExistInMatchException(Long matchId) {
		super(ErrorCode.ROUND_NOT_FINISHED, "[매치 번호 : " + matchId + "]");
	}

	public LeagueParticipationNotExistInMatchException(Long matchId, Exception e) {
		super(ErrorCode.ROUND_NOT_FINISHED, "[매치 번호 : " + matchId + "]", e);
	}
}
