package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class SetScoreNotReachedException extends BadmintonException {

	public SetScoreNotReachedException(int team1Score, int team2Score) {
		super(ErrorCode.SET_SCORE_NOT_REACHED, "[팀1 점수 : " + team1Score + " 팀2 점수 : " + team2Score + "]");
	}

	public SetScoreNotReachedException(int team1Score, int team2Score, Exception e) {
		super(ErrorCode.SET_SCORE_NOT_REACHED, "[팀1 점수 : " + team1Score + " 팀2 점수 : " + team2Score + "]", e);
	}
}
