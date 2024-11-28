package org.badminton.api.application.match;

import org.badminton.api.interfaces.match.dto.StartMatchCommand;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;

public interface MatchOperationHandler {
	BracketInfo generateInitialBracket(Long leagueId, String memberToken);

	SetInfo.Main registerSetScoreInMatch(Long leagueId, Long matchId, Integer setIndex,
		MatchCommand.UpdateSetScore updateSetScoreCommand, String memberToken);

	void startMatch(Long leagueId, StartMatchCommand startMatchCommand);
}
