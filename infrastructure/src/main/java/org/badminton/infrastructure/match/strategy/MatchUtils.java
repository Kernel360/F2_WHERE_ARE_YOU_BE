package org.badminton.infrastructure.match.strategy;

import static org.badminton.domain.common.consts.Constants.*;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.vo.Team;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.reader.DoublesMatchReader;
import org.badminton.domain.domain.match.reader.SinglesMatchReader;
import org.badminton.domain.domain.match.store.DoublesMatchStore;
import org.badminton.domain.domain.match.store.SinglesMatchStore;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MatchUtils {

	private final SinglesMatchStore singlesMatchStore;
	private final SinglesMatchReader singlesMatchReader;
	private final LeagueParticipantReader leagueParticipantReader;
	private final DoublesMatchReader doublesMatchReader;
	private final DoublesMatchStore doublesMatchStore;

	// 다음 라운드 matchId 계산
	private static Integer determineNextRoundMatch(int matchId, int totalParticipants,
		int roundStartId) {

		// 전체 라운드 구하기
		int totalRounds = calculateTotalRounds(totalParticipants);
		// 매치의 현재 라운드 계산
		int matchCountInRound = (totalParticipants + 1) / 2;

		// for 문으로 돌면서 전체 라운드 수만큼 돌면서 현재 matchId, 현재 라운드의 startId, 현재 라운드의 번호를 이용해 어떤 라운드에 속하는지 확인하고 다음 라운드의 매치 아이디 계산
		for (int round = FIRST_ROUND_NUMBER; round <= totalRounds; round++) {
			if (isCurrentMatchInRound(matchId, roundStartId, matchCountInRound)) {
				return calculateNextRoundMatchId(matchId, roundStartId, matchCountInRound);
			}
			roundStartId += matchCountInRound;
			matchCountInRound = (matchCountInRound + 1) / 2;
		}
		return null;
	}

	// 다음 라운드 matchId 계산 로직만 분리
	private static int calculateNextRoundMatchId(int currentMatchId, int currentRoundStartId,
		int matchesInCurrentRound) {
		int matchIndexInRound = currentMatchId - currentRoundStartId;
		int nextMatchIndexInRound = matchIndexInRound / 2;
		int nextRoundStartId = currentRoundStartId + matchesInCurrentRound;

		return nextRoundStartId + nextMatchIndexInRound;
	}

	private static boolean isCurrentMatchInRound(int matchId, int roundStartId,
		int matchesInCurrentRound) {
		return isMatchIdGreaterThanOrEqualToRoundStart(matchId, roundStartId)
			&& isMatchIdLessThanRoundEnd(matchId, roundStartId, matchesInCurrentRound);
	}

	private static boolean isMatchIdLessThanRoundEnd(int currentMatchId, int currentRoundStartId,
		int matchesInCurrentRound) {
		return currentMatchId < currentRoundStartId + matchesInCurrentRound;
	}

	private static boolean isMatchIdGreaterThanOrEqualToRoundStart(int currentMatchId, int currentRoundStartId) {
		return currentMatchId >= currentRoundStartId;
	}

	// 전체 라운드 계산
	public static int calculateTotalRounds(int currentTeams) {
		int round = INIT_ROUND_NUMBER;
		while (currentTeams > 1) {
			currentTeams = (currentTeams + 1) / 2;
			round++;
		}
		return round;
	}

	public void updateSinglesMatchNextRoundMatch(SinglesMatch singlesMatch) {
		LeagueParticipant winner = singlesMatch.determineWinner();
		if (winner == LeagueParticipant.emptyWinner()) {
			return;
		}

		int totalRounds = singlesMatch.getLeague().getTotalRounds();
		if (singlesMatch.getRoundNumber() == totalRounds) {
			return;
		}

		SinglesMatch startMatch = singlesMatchReader.findFirstMatchByLeagueId(
			singlesMatch.getLeague().getLeagueId());

		int nextRoundMatchId = MatchUtils.determineNextRoundMatch(
			Math.toIntExact(singlesMatch.getId()),
			leagueParticipantReader.countParticipantMember(singlesMatch.getLeague().getLeagueId()),
			Math.toIntExact(startMatch.getId()));

		SinglesMatch nextRoundMatch = singlesMatchReader.getSinglesMatch((long)nextRoundMatchId);

		if (nextRoundMatch == null) {
			return;
		}

		if (singlesMatch.getMatchStatus() == MatchStatus.BYE) {
			nextRoundMatch.defineLeagueParticipant1(winner);
			singlesMatchStore.store(nextRoundMatch);
			return;
		}

		assignSinglesWinnerToNextRound(nextRoundMatch, winner);
		singlesMatchStore.store(nextRoundMatch);
	}

	private void assignSinglesWinnerToNextRound(SinglesMatch nextRoundMatch, LeagueParticipant winner) {
		if (!nextRoundMatch.isLeagueParticipant1Exist()) {
			nextRoundMatch.defineLeagueParticipant1(winner);
			return;
		}

		if (!nextRoundMatch.isLeagueParticipant2Exist()) {
			nextRoundMatch.defineLeagueParticipant2(winner);
		}
	}

	public void updateDoublesMatchNextRoundMatch(DoublesMatch doublesMatch) {
		Team winner = doublesMatch.determineWinner();
		if (winner == Team.emptyWinner()) {
			return;
		}

		int totalRounds = doublesMatch.getLeague().getTotalRounds();
		if (doublesMatch.getRoundNumber() == totalRounds) {
			return;
		}

		DoublesMatch startMatch = doublesMatchReader.findFirstMatchByLeagueId(
			doublesMatch.getLeague().getLeagueId());

		int nextRoundMatchId = MatchUtils.determineNextRoundMatch(Math.toIntExact(doublesMatch.getId()),
			leagueParticipantReader.countParticipantMember(doublesMatch.getLeague().getLeagueId())
				/ PARTICIPANTS_PER_TEAM, Math.toIntExact(startMatch.getId()));

		DoublesMatch nextRoundMatch = doublesMatchReader.getDoublesMatch((long)nextRoundMatchId);

		if (nextRoundMatch == null) {
			return;
		}

		if (doublesMatch.getMatchStatus() == MatchStatus.BYE) {
			nextRoundMatch.defineTeam1(winner);
			doublesMatchStore.store(nextRoundMatch);
			return;
		}

		assignDoublesWinnerToNextRound(nextRoundMatch, winner);
		doublesMatchStore.store(nextRoundMatch);
	}

	private void assignDoublesWinnerToNextRound(DoublesMatch nextRoundMatch, Team winner) {
		if (!nextRoundMatch.isTeam1Exist()) {
			nextRoundMatch.defineTeam1(winner);
			return;
		}
		if (!nextRoundMatch.isTeam2Exist()) {
			nextRoundMatch.defineTeam2(winner);
		}

	}

}

