package org.badminton.api.application.match;

import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.service.BracketGenerationService;
import org.badminton.domain.domain.match.service.MatchProgressService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FreeMatchFacade implements MatchOperationHandler {
	private final BracketGenerationService freeBracketGenerationService;
	private final MatchProgressService matchProgressService;

	public FreeMatchFacade(
		@Qualifier("freeBracketGenerationServiceImpl") BracketGenerationService freeBracketGenerationService,
		@Qualifier("freeMatchProgressServiceImpl") MatchProgressService freematchProgressService) {

		this.freeBracketGenerationService = freeBracketGenerationService;
		this.matchProgressService = freematchProgressService;
	}

	@Override
	public SetInfo.Main registerSetScoreInMatch(Long leagueId, Long matchId, int setIndex,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		MatchStrategy matchStrategy = matchProgressService.makeSinglesOrDoublesMatchStrategy(leagueId);
		return matchProgressService.registerSetScoreInMatch(matchStrategy, leagueId, matchId, setIndex,
			updateSetScoreCommand);
	}

	@Override
	public BracketInfo generateInitialBracket(Long leagueId) {
		freeBracketGenerationService.checkLeagueRecruitingStatus(leagueId);
		MatchStrategy matchStrategy = freeBracketGenerationService.makeSinglesOrDoublesMatchStrategy(leagueId);
		return freeBracketGenerationService.makeBracket(matchStrategy, leagueId);
	}

}
