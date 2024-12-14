package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class TieScoreNotAllowedException extends BadmintonException {

	public TieScoreNotAllowedException(int team1Score, int team2Score) {
		super(ErrorCode.TIE_SCORE_NOT_ALLOWED, "[팀1 점수 : " + team1Score + " 팀2 점수 : " + team2Score + "]");
	}

	public TieScoreNotAllowedException(int team1Score, int team2Score, Exception e) {
		super(ErrorCode.TIE_SCORE_NOT_ALLOWED, "[팀1 점수 : " + team1Score + " 팀2 점수 : " + team2Score + "]", e);
	}
}
