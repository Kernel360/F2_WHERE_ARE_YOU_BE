package org.badminton.infrastructure.match.service;

import static org.badminton.domain.common.consts.Constants.*;

import java.util.ArrayList;
import java.util.List;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.vo.Team;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.infrastructure.match.strategy.MatchUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TournamentDoublesBracketCreator {

	private final MatchUtils matchUtils;
	private final DoublesMatchStore doublesMatchStore;
	private final DoublesMatchReader doublesMatchReader;

	private static boolean isParticipantOddSize(List<LeagueParticipant> participants) {
		return participants.size() % 4 != 0;
	}

	private static boolean isDoubleMatchOddSize(List<DoublesMatch> doublesMatches) {
		return doublesMatches.size() % 2 != 0;
	}

	public int getTotalRounds(League league, List<LeagueParticipant> currentParticipants) {

		int totalRounds = MatchUtils.calculateTotalRounds(currentParticipants.size() / PARTICIPANTS_PER_TEAM);

		league.defineTotalRounds(totalRounds);
		return totalRounds;
	}

	public void generateAllMatches(League league, List<DoublesMatch> allMatches,
		List<LeagueParticipant> currentParticipants,
		int totalRounds) {

		allMatches.addAll(createFirstRoundMatches(league, currentParticipants));

		allMatches.addAll(createSubsequentRoundsMatches(league, totalRounds));

		allMatches.stream()
			.filter(DoublesMatch::isByeMatch)
			.forEach(matchUtils::updateDoublesMatchNextRoundMatch);
	}

	private List<DoublesMatch> createFirstRoundMatches(League league, List<LeagueParticipant> participants) {
		List<DoublesMatch> matches = new ArrayList<>();

		for (int i = 0; i < participants.size() - 2; i += PARTICIPANTS_PER_TEAM * TEAMS_PER_MATCH) {
			Team team1 = new Team(participants.get(i), participants.get(i + 1));
			Team team2 = new Team(participants.get(i + 2), participants.get(i + 3));
			DoublesMatch match = new DoublesMatch(league, team1, team2, FIRST_ROUND_NUMBER);

			makeSetsInMatch(match);
			doublesMatchStore.store(match);
			matches.add(match);
		}

		if (isParticipantOddSize(participants)) {
			Team byeTeam = new Team(participants.remove(participants.size() - 1),
				participants.get(participants.size() - 2));
			DoublesMatch byeMatch = new DoublesMatch(league, byeTeam, Team.emptyParticipant(), 1);
			byeMatch.byeMatch();
			doublesMatchStore.store(byeMatch);
			matches.add(byeMatch);
		}
		return matches;
	}

	private List<DoublesMatch> createMatchesRound(League league, List<DoublesMatch> previousRoundMatches,
		int roundNumber) {

		List<DoublesMatch> currentRoundMatches = new ArrayList<>(
			initializeRoundMatches(league, previousRoundMatches, roundNumber));

		if (isDoubleMatchOddSize(previousRoundMatches)) {
			currentRoundMatches.add(createByeRoundMatch(league, previousRoundMatches, roundNumber));
		}

		return currentRoundMatches;
	}

	private List<DoublesMatch> initializeRoundMatches(League league, List<DoublesMatch> previousRoundMatches,
		int roundNumber) {
		List<DoublesMatch> regularRoundMatches = new ArrayList<>();

		for (int i = 0; i < previousRoundMatches.size() - 1; i += TEAMS_PER_MATCH) {
			DoublesMatch match = new DoublesMatch(league, Team.emptyParticipant(), Team.emptyParticipant(),
				roundNumber);
			makeSetsInMatch(match);
			doublesMatchStore.store(match);
			regularRoundMatches.add(match);
		}

		return regularRoundMatches;
	}

	private DoublesMatch createByeRoundMatch(League league, List<DoublesMatch> previousRoundMatches,
		int roundNumber) {
		DoublesMatch byeMatch = previousRoundMatches.get(previousRoundMatches.size() - 1);
		Team winner = byeMatch.determineWinner();

		DoublesMatch nextByeMatch = new DoublesMatch(league, winner, Team.emptyParticipant(), roundNumber);
		nextByeMatch.byeMatch();
		doublesMatchStore.store(nextByeMatch);

		return nextByeMatch;
	}

	private List<DoublesMatch> createSubsequentRoundsMatches(League league, int totalRounds) {
		List<DoublesMatch> matches = new ArrayList<>();
		List<DoublesMatch> previousMatches = doublesMatchReader.findMatchesByLeagueAndRound(league.getLeagueId(),
			FIRST_ROUND_NUMBER);

		for (int roundNumber = SECOND_ROUND_NUMBER; roundNumber <= totalRounds; roundNumber++) {
			List<DoublesMatch> currentRoundMatches = createMatchesRound(league, previousMatches, roundNumber);
			matches.addAll(currentRoundMatches);
			previousMatches = currentRoundMatches;
		}

		return matches;
	}

	private void makeSetsInMatch(DoublesMatch doublesMatch) {
		for (int i = 1; i <= SET_COUNT; i++) {
			DoublesSet set = new DoublesSet(doublesMatch, i);
			doublesMatch.addSet(set);
		}
		doublesMatchStore.store(doublesMatch);
	}
}
