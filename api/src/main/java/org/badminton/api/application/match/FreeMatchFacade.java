package org.badminton.api.application.match;

import org.badminton.api.interfaces.match.dto.InitFirstSetScoreCommand;
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
public class FreeMatchFacade implements MatchOperationHandler {
	private final BracketGenerationService freeBracketGenerationService;
	private final MatchProgressService matchProgressService;
	private final MatchRecordService matchRecordService;

	public FreeMatchFacade(
		@Qualifier("freeBracketGenerationServiceImpl") BracketGenerationService freeBracketGenerationService,
		@Qualifier("freeMatchProgressServiceImpl") MatchProgressService freematchProgressService,
		MatchRecordService matchRecordService) {

		this.freeBracketGenerationService = freeBracketGenerationService;
		this.matchProgressService = freematchProgressService;
		this.matchRecordService = matchRecordService;
	}

	@Override
	public SetInfo.Main registerSetScoreInMatch(Long leagueId, Long matchId, int setIndex,
		MatchCommand.UpdateSetScore updateSetScoreCommand, String memberToken) {
		MatchStrategy matchStrategy = matchProgressService.makeSinglesOrDoublesMatchStrategy(leagueId);
		SetInfo.Main main = matchProgressService.registerSetScoreInMatch(matchStrategy, leagueId, matchId, setIndex,
			updateSetScoreCommand, memberToken);
		matchRecordService.processMatchResult(main.getMatchType(), matchId);

		return main;
	}

	@Override
	public void registerFirstSetScoreInMatch(InitFirstSetScoreCommand initFirstSetScoreCommand) {
		MatchStrategy matchStrategy = freeBracketGenerationService.makeSinglesOrDoublesMatchStrategy(
			initFirstSetScoreCommand.leagueId());
		freeBracketGenerationService.initMatch(matchStrategy, initFirstSetScoreCommand.matchId());
	}

	@Override
	public BracketInfo generateInitialBracket(Long leagueId) {
		freeBracketGenerationService.checkLeagueRecruitingStatus(leagueId);
		MatchStrategy matchStrategy = freeBracketGenerationService.makeSinglesOrDoublesMatchStrategy(leagueId);
		return freeBracketGenerationService.makeBracket(matchStrategy, leagueId);
	}

}
