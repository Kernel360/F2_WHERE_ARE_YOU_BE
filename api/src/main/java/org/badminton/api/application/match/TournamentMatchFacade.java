package org.badminton.api.application.match;

import org.badminton.api.interfaces.match.dto.StartFirstSetScoreCommand;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.service.BracketGenerationService;
import org.badminton.domain.domain.match.service.MatchProgressService;
import org.badminton.domain.domain.match.service.MatchRecordService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TournamentMatchFacade implements MatchOperationHandler {
	private final BracketGenerationService tournamentBracketGenerationService;
	private final MatchProgressService tournamentMatchProgressService;
	private final MatchRecordService matchRecordService;

	public TournamentMatchFacade(
		@Qualifier("tournamentBracketGenerationServiceImpl") BracketGenerationService tournamentBracketGenerationService,
		@Qualifier("tournamentMatchProgressServiceImpl") MatchProgressService tournamentMatchProgressService,
		MatchRecordService matchRecordService) {

		this.tournamentBracketGenerationService = tournamentBracketGenerationService;
		this.tournamentMatchProgressService = tournamentMatchProgressService;
		this.matchRecordService = matchRecordService;
	}

	@Override
	public BracketInfo generateInitialBracket(Long leagueId, String memberToken) {
		tournamentBracketGenerationService.checkLeagueRecruitingStatus(leagueId);
		MatchStrategy matchStrategy = tournamentBracketGenerationService.makeSinglesOrDoublesMatchStrategy(leagueId);
		return tournamentBracketGenerationService.makeBracket(matchStrategy, leagueId, memberToken);
	}

	@Override
	public SetInfo.Main registerSetScoreInMatch(Long leagueId, Long matchId, Integer setIndex,
		MatchCommand.UpdateSetScore updateSetScoreCommand, String memberToken) {
		MatchStrategy matchStrategy = tournamentMatchProgressService.makeSinglesOrDoublesMatchStrategy(leagueId);
		SetInfo.Main main = tournamentMatchProgressService.registerSetScoreInMatch(matchStrategy, leagueId, matchId,
			setIndex, updateSetScoreCommand, memberToken);
		matchRecordService.processMatchResult(main.getMatchType(), matchId);
		return main;
	}

	@Override
	public void registerFirstSetScoreInMatch(StartFirstSetScoreCommand initFirstSetScoreCommand) {
		tournamentBracketGenerationService.checkLeagueRecruitingStatus(initFirstSetScoreCommand.leagueId());

	}
}
