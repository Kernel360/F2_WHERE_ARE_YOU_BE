package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class MatchAlreadyStartedExceptionWhenBracketGenerationException extends BadmintonException {
	public MatchAlreadyStartedExceptionWhenBracketGenerationException(Long leagueId) {
		super(ErrorCode.MATCH_ALREADY_STARTED_WHEN_BRACKET_GENERATION, "[경기 아이디 : " + leagueId + " ]");
	}

	public MatchAlreadyStartedExceptionWhenBracketGenerationException(Long leagueId, Exception e) {
		super(ErrorCode.MATCH_ALREADY_STARTED_WHEN_BRACKET_GENERATION, "[경기 아이디 : " + leagueId + " ]", e);
	}
}
