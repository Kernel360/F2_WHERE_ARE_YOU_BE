package org.badminton.domain.domain.match.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public abstract class AbstractDoublesMatchStrategy implements MatchStrategy {

	private final DoublesMatchReader doublesMatchReader;

	@Override
	public abstract void checkDuplicateInitialBracket(Long leagueId);

	@Override
	public BracketInfo retrieveBracketInLeague(Long leagueId) {
		List<DoublesMatch> bracketInLeague = doublesMatchReader.getDoublesBracket(leagueId);
		return BracketInfo.fromDoubles(1, bracketInLeague);
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
	public SetInfo.Main registerSetScoreInMatch(Long matchId, int setIndex,
		MatchCommand.UpdateSetScore updateSetScoreCommand) {
		return null;
	}

	@Override
	public boolean isMatchInLeague(Long leagueId) {
		return !doublesMatchReader.checkIfBracketEmpty(leagueId);
	}

	@Override
	public List<LeagueSetsScoreInProgressInfo> retrieveLeagueSetsScoreInProgress(Long leagueId) {
		List<DoublesMatch> doublesBracket = doublesMatchReader.getDoublesBracket(leagueId);
		Map<DoublesMatch, DoublesSet> matchSetsInProgress = doublesBracket.stream()
			.filter(doublesMatch -> doublesMatch.getMatchStatus() == MatchStatus.IN_PROGRESS)
			.flatMap(doublesMatch -> doublesMatch.getDoublesSets().stream()
				.filter(set -> set.getSetStatus() == SetStatus.IN_PROGRESS)
				.findFirst()
				.map(set -> Map.entry(doublesMatch, set))
				.stream())
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		return matchSetsInProgress.entrySet().stream()
			.map(entry -> LeagueSetsScoreInProgressInfo.fromDoublesMatchAndSet(entry.getKey(), entry.getValue()))
			.toList();
	}

	@Override
	public MatchSetInfo retrieveMatchSet(Long matchId, int setNumber) {
		DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
		DoublesSet doublesSet = doublesMatch.getDoublesSet(setNumber);
		return MatchSetInfo.fromDoubles(doublesSet);
	}
}
