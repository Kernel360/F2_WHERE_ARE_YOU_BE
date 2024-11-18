package org.badminton.domain.domain.match.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.SetStatus;
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
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public abstract class AbstractSinglesMatchStrategy implements MatchStrategy {

	public static final int FIRST_ROUND_NUMBER = 1;
	private final SinglesMatchReader singlesMatchReader;

	@Override
	public abstract void checkDuplicateInitialBracket(Long leagueId);

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
	public abstract SetInfo.Main registerSetScoreInMatch(Long matchId, int setIndex,
		MatchCommand.UpdateSetScore updateSetScoreCommand);

	@Override
	public boolean isMatchInLeague(Long leagueId) {
		return !singlesMatchReader.checkIfBracketEmpty(leagueId);
	}

	// TODO: 리팩토링
	@Override
	public List<LeagueSetsScoreInProgressInfo> retrieveLeagueSetsScoreInProgress(Long leagueId) {
		List<SinglesMatch> singlesBracket = singlesMatchReader.getSinglesBracket(leagueId);
		Map<SinglesMatch, SinglesSet> matchSetsInProgress = singlesBracket.stream()
			.filter(singlesMatch -> singlesMatch.getMatchStatus() == MatchStatus.IN_PROGRESS)
			.flatMap(singlesMatch -> singlesMatch.getSinglesSets().stream()
				.filter(set -> set.getSetStatus() == SetStatus.IN_PROGRESS)
				.findFirst()
				.map(set -> Map.entry(singlesMatch, set))
				.stream())
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		return matchSetsInProgress.entrySet().stream()
			.map(entry -> LeagueSetsScoreInProgressInfo.fromSinglesMatchAndSet(entry.getKey(), entry.getValue()))
			.toList();
	}

	@Override
	public MatchSetInfo retrieveMatchSet(Long matchId, int setNumber) {
		SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
		SinglesSet singlesSet = singlesMatch.getSinglesSet(setNumber);
		return MatchSetInfo.fromSingles(singlesSet);
	}
}
