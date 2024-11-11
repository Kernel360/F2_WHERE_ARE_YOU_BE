package org.badminton.api.application.match;

import java.util.List;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.service.MatchRetrieveService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MatchFacade {

	private final MatchOperationHandler freeMatchFacade;
	private final MatchOperationHandler tournamentMatchFacade;
	private final MatchRetrieveService freeMatchRetrieveService;
	private final MatchRetrieveService tournamentMatchRetrieveService;
	private final LeagueReader leagueReader;

	public MatchFacade(
		@Qualifier("freeMatchFacade") MatchOperationHandler freeMatchFacade,
		@Qualifier("tournamentMatchFacade") MatchOperationHandler tournamentMatchFacade,
		@Qualifier("freeMatchRetrieveServiceImpl") MatchRetrieveService freeMatchRetrieveService,
		@Qualifier("tournamentMatchRetrieveServiceImpl") MatchRetrieveService tournamentMatchRetrieveService,
		LeagueReader leagueReader) {
		this.freeMatchFacade = freeMatchFacade;
		this.tournamentMatchFacade = tournamentMatchFacade;
		this.freeMatchRetrieveService = freeMatchRetrieveService;
		this.tournamentMatchRetrieveService = tournamentMatchRetrieveService;
		this.leagueReader = leagueReader;
	}

	public MatchOperationHandler getMatchOperationHandler(Long leagueId) {
		MatchGenerationType matchGenerationType = leagueReader.getMatchGenerationTypeByLeagueId(leagueId);
		return matchGenerationType == MatchGenerationType.FREE ? freeMatchFacade : tournamentMatchFacade;
	}

	private MatchRetrieveService getMatchRetrieveService(Long leagueId) {
		MatchGenerationType matchGenerationType = leagueReader.getMatchGenerationTypeByLeagueId(leagueId);
		return matchGenerationType == MatchGenerationType.FREE ? freeMatchRetrieveService :
			tournamentMatchRetrieveService;
	}

	public BracketInfo retrieveBracket(Long leagueId) {
		MatchRetrieveService matchRetrieveService = getMatchRetrieveService(leagueId);
		MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
		return matchRetrieveService.retrieveBracket(matchStrategy, leagueId);
	}

	public List<SetInfo.Main> retrieveAllSetsScoreInBracket(Long leagueId) {
		MatchRetrieveService matchRetrieveService = getMatchRetrieveService(leagueId);
		MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
		return matchRetrieveService.retrieveAllSetsScoreInBracket(matchStrategy, leagueId);
	}

	public MatchInfo.SetScoreDetails retrieveAllSetsScoreInMatch(Long leagueId, Long matchId) {
		MatchRetrieveService matchRetrieveService = getMatchRetrieveService(leagueId);
		MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
		return matchRetrieveService.retrieveAllSetsScoreInMatch(matchStrategy, matchId);
	}

	public List<LeagueSetsScoreInProgressInfo> retrieveLeagueMatchSetsScoreInProgress(Long leagueId) {
		MatchRetrieveService matchRetrieveService = getMatchRetrieveService(leagueId);
		MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
		return matchRetrieveService.retrieveLeagueMatchInProgress(matchStrategy, leagueId);
	}

	public MatchSetInfo retrieveMatchSetInfo(Long leagueId, Long matchId, int setNumber) {
		MatchRetrieveService matchRetrieveService = getMatchRetrieveService(leagueId);
		MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(matchId);
		return matchRetrieveService.retrieveMatchSet(matchStrategy, matchId, setNumber);
	}
}
