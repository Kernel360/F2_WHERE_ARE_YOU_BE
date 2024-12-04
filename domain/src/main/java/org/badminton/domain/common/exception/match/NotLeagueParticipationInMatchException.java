package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class NotLeagueParticipationInMatchException extends BadmintonException {

	public NotLeagueParticipationInMatchException(Long matchId) {
		super(ErrorCode.ROUND_NOT_FINISHED, "[매치 번호 : " + matchId + "]");
	}

	public NotLeagueParticipationInMatchException(Long matchId, Exception e) {
		super(ErrorCode.ROUND_NOT_FINISHED, "[매치 번호 : " + matchId + "]", e);
	}
}
