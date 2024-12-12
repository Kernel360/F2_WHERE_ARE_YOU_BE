package org.badminton.domain.domain.match.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.common.exception.match.MatchAlreadyStartedExceptionWhenBracketGenerationException;
import org.badminton.domain.common.exception.match.RoundNotFinishedException;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.entity.SinglesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.reader.SinglesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchStore;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public abstract class AbstractSinglesMatchStrategy implements MatchStrategy {

	public static final int FIRST_ROUND_NUMBER = 1;
	private final SinglesMatchReader singlesMatchReader;
	private final SinglesMatchStore singlesMatchStore;

	@Override
	public void checkDuplicateInitialBracket(Long leagueId) {
		if (singlesMatchReader.checkIfBracketEmpty(leagueId)) {
			return;
		}
		if (singlesMatchReader.allMatchesNotStartedForLeague(leagueId)) {
			singlesMatchStore.deleteSinglesBracket(leagueId);
			return;
		}
		throw new MatchAlreadyStartedExceptionWhenBracketGenerationException(leagueId);
	}

	@Override
	public BracketInfo retrieveBracketInLeague(Long leagueId) {
		List<SinglesMatch> bracketInLeague = singlesMatchReader.getSinglesBracket(leagueId);
		return BracketInfo.fromSingles(FIRST_ROUND_NUMBER, bracketInLeague);
	}

	@Override
	public List<SetInfo.Main> retrieveAllSetsScoreInLeague(Long leagueId) {
		return singlesMatchReader.getSinglesBracket(leagueId)
			.stream()
			.flatMap(singlesMatch ->
				singlesMatch.getSinglesSets().stream()
					.map(singlesSet -> SetInfo.fromSinglesSet(
						singlesMatch.getId(),
						singlesSet.getSetNumber(), singlesSet))
			)
			.toList();
	}

	@Override
	public MatchInfo.SetScoreDetails retrieveAllSetsScoreInMatch(Long matchId) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
		return MatchInfo.SetScoreDetails.fromSinglesMatchToMatchDetails(singlesMatch);
	}

	@Override
	public abstract BracketInfo makeBracket(League league, List<LeagueParticipant> leagueParticipantList);

	@Override
	public abstract SetInfo.Main endSet(Long matchId, Integer setIndex,
		MatchCommand.UpdateSetScore updateSetScoreCommand);

	@Override
	public boolean isMatchInLeague(Long leagueId) {
		return !singlesMatchReader.checkIfBracketEmpty(leagueId);
	}

	// TODO: 리팩토링
	@Override
	public List<LeagueSetsScoreInProgressInfo> retrieveLeagueSetsScoreInProgress(Long leagueId) {
		List<SinglesMatch> singlesMatchInProgress = singlesMatchReader.getSinglesBracket(leagueId)
			.stream()
			.filter(singlesMatch -> singlesMatch.getMatchStatus() == MatchStatus.IN_PROGRESS)
			.filter(singlesMatch -> singlesMatch.getSetInProgress().isPresent())
			.sorted(Comparator.comparing(SinglesMatch::getId))
			.toList();

		List<SinglesSet> singlesSetInProgress = singlesMatchInProgress.stream()
			.filter(singlesMatch -> singlesMatch.getSetInProgress().isPresent())
			.map(singlesMatch -> singlesMatch.getSetInProgress().get())
			.toList();

		if (singlesMatchInProgress.size() != singlesSetInProgress.size()) {
			throw new BadmintonException(ErrorCode.INVALID_PARAMETER);
		}

		List<LeagueSetsScoreInProgressInfo> leagueSetsScoreInProgressInfo = new ArrayList<>();

		for (int i = 0; i < singlesSetInProgress.size(); i++) {
			leagueSetsScoreInProgressInfo.add(LeagueSetsScoreInProgressInfo.fromSinglesMatchAndSet(
				singlesMatchInProgress.get(i), singlesSetInProgress.get(i)
			));
		}
		return leagueSetsScoreInProgressInfo;
	}

	@Override
	public MatchSetInfo retrieveMatchSet(Long matchId, int setNumber) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
		SinglesSet singlesSet = singlesMatch.getSinglesSet(setNumber);
		return MatchSetInfo.fromSingles(singlesSet);
	}

	@Override
	public void startMatch(Long matchId) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
		validatePreviousRoundCompletion(singlesMatch.getLeague().getLeagueId(), singlesMatch.getRoundNumber());
		singlesMatch.startMatch();
		singlesMatch.getSinglesSet(1).initMatch();
		singlesMatchStore.store(singlesMatch);
	}

	private void validatePreviousRoundCompletion(Long leagueId, int roundNumber) {

		if (roundNumber == 1) {
			return;
		}
		int previousRoundNumber = roundNumber - 1;

		if (!singlesMatchReader.allRoundMatchesDone(leagueId, previousRoundNumber)) {
			throw new RoundNotFinishedException(previousRoundNumber);
		}
	}
}
