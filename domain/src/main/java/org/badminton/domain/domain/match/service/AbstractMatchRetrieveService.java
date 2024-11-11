package org.badminton.domain.domain.match.service;

import java.util.List;

import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public abstract class AbstractMatchRetrieveService implements MatchRetrieveService {

	@Override
	public BracketInfo retrieveBracket(MatchStrategy matchStrategy, Long leagueId) {
		return matchStrategy.retrieveBracketInLeague(leagueId);
	}

	@Override
	public List<SetInfo.Main> retrieveAllSetsScoreInBracket(MatchStrategy matchStrategy, Long leagueId) {
		return matchStrategy.retrieveAllSetsScoreInLeague(leagueId);
	}

	@Override
	public MatchInfo.SetScoreDetails retrieveAllSetsScoreInMatch(MatchStrategy matchStrategy, Long matchId) {
		return matchStrategy.retrieveAllSetsScoreInMatch(matchId);
	}

	@Override
	public boolean isMatchInLeague(MatchStrategy matchStrategy, Long leagueId) {
		return matchStrategy.isMatchInLeague(leagueId);
	}

	@Override
	public List<LeagueSetsScoreInProgressInfo> retrieveLeagueMatchInProgress(MatchStrategy matchStrategy,
		Long leagueId) {
		return matchStrategy.retrieveLeagueSetsScoreInProgress(leagueId);
	}

	@Override
	public MatchSetInfo retrieveMatchSet(MatchStrategy matchStrategy, Long matchId, int setNumber) {
		return matchStrategy.retrieveMatchSet(matchId, setNumber);
	}

	public abstract MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId);

}
