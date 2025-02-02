package org.badminton.domain.domain.match.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.common.exception.match.MatchAlreadyStartedExceptionWhenBracketGenerationException;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.reader.DoublesMatchReader;
import org.badminton.domain.domain.match.store.DoublesMatchStore;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public abstract class AbstractDoublesMatchStrategy implements MatchStrategy {

	public static final int FIRST_ROUND_NUMBER = 1;
	private final DoublesMatchReader doublesMatchReader;
	private final DoublesMatchStore doublesMatchStore;

	@Override
	public void checkDuplicateInitialBracket(Long leagueId) {
		if (doublesMatchReader.checkIfBracketEmpty(leagueId)) {
			return;
		}
		if (doublesMatchReader.allMatchesNotStartedForLeague(leagueId)) {
			doublesMatchStore.deleteDoublesBracket(leagueId);
			return;
		}
		throw new MatchAlreadyStartedExceptionWhenBracketGenerationException(leagueId);
	}

	@Override
	public BracketInfo retrieveBracketInLeague(Long leagueId) {
		List<DoublesMatch> bracketInLeague = doublesMatchReader.getDoublesBracket(leagueId);
		return BracketInfo.fromDoubles(FIRST_ROUND_NUMBER, bracketInLeague);
	}

	@Override
	public List<SetInfo.Main> retrieveAllSetsScoreInLeague(Long leagueId) {
		return doublesMatchReader.getDoublesBracket(leagueId).stream()
			.flatMap(doublesMatch ->
				doublesMatch.getDoublesSets().stream()
					.map(doublesSet -> SetInfo.fromDoublesSet(doublesMatch.getId(),
						doublesSet.getSetNumber(), doublesSet))
			)
			.toList();
	}

	@Override
	public MatchInfo.SetScoreDetails retrieveAllSetsScoreInMatch(Long matchId) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
		return MatchInfo.SetScoreDetails.fromDoublesMatchToMatchDetails(doublesMatch);
	}

	@Override
	public abstract BracketInfo makeBracket(League league, List<LeagueParticipant> leagueParticipantList);

	@Override
	public boolean isMatchInLeague(Long leagueId) {
		return !doublesMatchReader.checkIfBracketEmpty(leagueId);
	}

	@Override
	public List<LeagueSetsScoreInProgressInfo> retrieveLeagueSetsScoreInProgress(Long leagueId) {
		List<DoublesMatch> doublesMatchInProgress = doublesMatchReader.getDoublesBracket(leagueId).stream()
			.filter(doublesMatch -> doublesMatch.getMatchStatus() == MatchStatus.IN_PROGRESS)
			.filter(doublesMatch -> doublesMatch.getSetInProgress().isPresent())
			.sorted(Comparator.comparing(DoublesMatch::getId))
			.toList();

		List<DoublesSet> doublesSetInProgress = doublesMatchInProgress.stream()
			.filter(doublesMatch -> doublesMatch.getSetInProgress().isPresent())
			.map(doublesMatch -> doublesMatch.getSetInProgress().get())
			.toList();

		if (doublesSetInProgress.size() != doublesMatchInProgress.size()) {
			throw new BadmintonException(ErrorCode.INVALID_PARAMETER);
		}

		List<LeagueSetsScoreInProgressInfo> leagueSetsScoreInProgressInfo = new ArrayList<>();

		for (int i = 0; i < doublesSetInProgress.size(); i++) {
			leagueSetsScoreInProgressInfo.add(LeagueSetsScoreInProgressInfo.fromDoublesMatchAndSet(
				doublesMatchInProgress.get(i), doublesSetInProgress.get(i)
			));
		}
		return leagueSetsScoreInProgressInfo;
	}

	@Override
	public MatchSetInfo retrieveMatchSet(Long matchId, int setNumber) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
		DoublesSet doublesSet = doublesMatch.getDoublesSet(setNumber);
		return MatchSetInfo.fromDoubles(doublesSet);
	}

	@Override
	public void startMatch(Long matchId) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
		doublesMatch.startMatchSet(1);
		doublesMatchStore.store(doublesMatch);
	}
}
