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
public class TournamentMatchFacade implements MatchOperationHandler {
	private final BracketGenerationService tournamentBracketGenerationService;
	private final MatchProgressService tournamentMatchProgressService;

	public TournamentMatchFacade(
		@Qualifier("tournamentBracketGenerationServiceImpl") BracketGenerationService tournamentBracketGenerationService,
		@Qualifier("tournamentMatchProgressServiceImpl") MatchProgressService tournamentMatchProgressService) {

		this.tournamentBracketGenerationService = tournamentBracketGenerationService;
		this.tournamentMatchProgressService = tournamentMatchProgressService;
	}

	@Override
	public BracketInfo generateInitialBracket(Long leagueId) {
		tournamentBracketGenerationService.checkLeagueRecruitingStatus(leagueId);
		MatchStrategy matchStrategy = tournamentBracketGenerationService.makeSinglesOrDoublesMatchStrategy(leagueId);
		return tournamentBracketGenerationService.makeBracket(matchStrategy, leagueId);
	}

	@Override
	public SetInfo.Main registerSetScoreInMatch(Long leagueId, Long matchId, int setIndex,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		MatchStrategy matchStrategy = tournamentMatchProgressService.makeSinglesOrDoublesMatchStrategy(leagueId);
		return tournamentMatchProgressService.registerSetScoreInMatch(matchStrategy, leagueId, matchId, setIndex,
			updateSetScoreCommand);
	}
}
