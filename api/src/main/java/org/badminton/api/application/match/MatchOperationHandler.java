package org.badminton.api.application.match;

import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;

public interface MatchOperationHandler {
	BracketInfo generateInitialBracket(Long leagueId);

	SetInfo.Main registerSetScoreInMatch(Long leagueId, Long matchId, int setIndex,
		MatchCommand.UpdateSetScore updateSetScoreCommand, String memberToken);
}
