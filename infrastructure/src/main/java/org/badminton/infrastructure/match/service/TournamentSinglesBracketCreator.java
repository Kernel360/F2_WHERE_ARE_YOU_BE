package org.badminton.infrastructure.match.service;

import static org.badminton.domain.common.consts.Constants.*;

import java.util.ArrayList;
import java.util.List;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.entity.SinglesSet;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.badminton.infrastructure.match.strategy.MatchUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TournamentSinglesBracketCreator {

	private final SinglesMatchStore singlesMatchStore;
	private final SinglesMatchReader singlesMatchReader;
	private final MatchUtils matchUtils;

	private static boolean isParticipantOddSize(List<LeagueParticipant> participants) {
		return participants.size() % 2 != 0;
	}

	private static boolean isSinglesMatchOddSize(List<SinglesMatch> singlesMatches) {
		return singlesMatches.size() % 2 != 0;
	}

	public int getTotalRounds(League league, List<LeagueParticipant> currentParticipants) {
		// 전체 라운드의 수 계산
		int totalRounds = MatchUtils.calculateTotalRounds(currentParticipants.size());

		// leagueEntity 에 전체 라운드 수 저장
		league.defineTotalRounds(totalRounds);
		return totalRounds;
	}

	public void generateAllMatches(League league, List<SinglesMatch> allMatches,
		List<LeagueParticipant> currentParticipants,
		int totalRounds) {
		// 전체 매치 배열에 첫번재 라운드 생성 후 넣기
		allMatches.addAll(createFirstRoundMatches(league, currentParticipants));

		// 전체 매치 배열에 첫번째 라운드 제외 모든 라운드 매치 생성 후 넣기
		allMatches.addAll(createSubsequentRoundsMatches(league, totalRounds));

		// 전체 매치 스트림 -> 부전승이고, leagueParticipant1 이 null 이 아닌 매치들 대상으로 -> leagueParticipant1 다음 라운드의 매치로 이동
		allMatches.stream()
			.filter(SinglesMatch::isByeMatch)
			.forEach(matchUtils::updateSinglesMatchNextRoundMatch);
	}

	private List<SinglesMatch> createFirstRoundMatches(League league, List<LeagueParticipant> participants) {
		List<SinglesMatch> matches = new ArrayList<>();

		for (int i = 0; i < participants.size() - 1; i += PARTICIPANTS_PER_MATCH) {
			SinglesMatch match = new SinglesMatch(league, participants.get(i), participants.get(i + 1),
				FIRST_ROUND_NUMBER);

			makeSetsInMatch(match);
			singlesMatchStore.store(match);
			matches.add(match);
		}

		if (isParticipantOddSize(participants)) {
			LeagueParticipant byeParticipant = participants.remove(participants.size() - 1);
			SinglesMatch byeMatch = new SinglesMatch(league, byeParticipant, LeagueParticipant.emptyParticipant(),
				FIRST_ROUND_NUMBER);

			byeMatch.byeMatch();
			singlesMatchStore.store(byeMatch);
			matches.add(byeMatch);
		}

		return matches;
	}

	private void makeSetsInMatch(SinglesMatch singlesMatch) {
		for (int i = 1; i <= SET_COUNT; i++) {
			SinglesSet set = new SinglesSet(singlesMatch, i);
			singlesMatch.addSet(set);
		}
		singlesMatchStore.store(singlesMatch);
	}

	private List<SinglesMatch> createSubsequentRoundsMatches(League league, int totalRounds) {
		List<SinglesMatch> matches = new ArrayList<>();
		List<SinglesMatch> previousRoundMatches = singlesMatchReader.findMatchesByLeagueAndRound(league.getLeagueId(),
			FIRST_ROUND_NUMBER);

		for (int roundNumber = SECOND_ROUND_NUMBER; roundNumber <= totalRounds; roundNumber++) {
			List<SinglesMatch> currentRoundMatches = createMatchesRound(league, previousRoundMatches, roundNumber);
			matches.addAll(currentRoundMatches);
			previousRoundMatches = currentRoundMatches;
		}

		return matches;
	}

	private List<SinglesMatch> createMatchesRound(League league, List<SinglesMatch> previousRoundMatches,
		int roundNumber) {

		List<SinglesMatch> currentRoundMatches = new ArrayList<>(
			initializeRoundMatches(league, previousRoundMatches, roundNumber));

		if (isSinglesMatchOddSize(previousRoundMatches)) {
			currentRoundMatches.add(createByeRoundMatch(league, previousRoundMatches, roundNumber));
		}

		return currentRoundMatches;
	}

	private List<SinglesMatch> initializeRoundMatches(League league, List<SinglesMatch> previousRoundMatches,
		int roundNumber) {
		List<SinglesMatch> regularRoundMatches = new ArrayList<>();

		for (int i = 0; i < previousRoundMatches.size() - 1; i += PARTICIPANTS_PER_MATCH) {
			SinglesMatch match = new SinglesMatch(league, LeagueParticipant.emptyParticipant(),
				LeagueParticipant.emptyParticipant(), roundNumber);
			makeSetsInMatch(match);
			singlesMatchStore.store(match);
			regularRoundMatches.add(match);
		}

		return regularRoundMatches;
	}

	private SinglesMatch createByeRoundMatch(League league, List<SinglesMatch> previousRoundMatches,
		int roundNumber) {
		SinglesMatch byeMatch = previousRoundMatches.get(previousRoundMatches.size() - 1);
		LeagueParticipant winner = byeMatch.determineWinner();

		SinglesMatch nextByeMatch = new SinglesMatch(league, winner, LeagueParticipant.emptyParticipant(),
			roundNumber);
		nextByeMatch.byeMatch();
		singlesMatchStore.store(nextByeMatch);

		return nextByeMatch;
	}

}
