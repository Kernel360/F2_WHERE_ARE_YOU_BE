package org.badminton.infrastructure.match.strategy;

import org.springframework.stereotype.Component;

@Component
public class MatchUtils {

	public static Integer calculateNextRoundMatchId(int currentMatchId, int totalParticipants, int startMatchId) {

		int totalRounds = calculateTotalRounds(totalParticipants);
		int matchesInCurrentRound = (totalParticipants + 1) / 2;
		int currentRoundStartId = startMatchId;

		for (int round = 1; round <= totalRounds; round++) {
			if (isCurrentMatchInRound(currentMatchId, currentRoundStartId, matchesInCurrentRound)) {
				return calculateNextMatchIdInRound(currentMatchId, currentRoundStartId, matchesInCurrentRound);
			}
			currentRoundStartId += matchesInCurrentRound;
			matchesInCurrentRound = (matchesInCurrentRound + 1) / 2;
		}
		return null;
	}

	private static int calculateNextMatchIdInRound(int currentMatchId, int currentRoundStartId,
		int matchesInCurrentRound) {
		int matchIndexInRound = currentMatchId - currentRoundStartId;
		int nextMatchIndexInRound = matchIndexInRound / 2;
		int nextRoundStartId = currentRoundStartId + matchesInCurrentRound;

		return nextRoundStartId + nextMatchIndexInRound;
	}

	private static boolean isCurrentMatchInRound(int currentMatchId, int currentRoundStartId,
		int matchesInCurrentRound) {
		return isMatchIdGreaterThanOrEqualToRoundStart(currentMatchId, currentRoundStartId)
			&& isMatchIdLessThanRoundEnd(currentMatchId, currentRoundStartId, matchesInCurrentRound);
	}

	private static boolean isMatchIdLessThanRoundEnd(int currentMatchId, int currentRoundStartId,
		int matchesInCurrentRound) {
		return currentMatchId < currentRoundStartId + matchesInCurrentRound;
	}

	private static boolean isMatchIdGreaterThanOrEqualToRoundStart(int currentMatchId, int currentRoundStartId) {
		return currentMatchId >= currentRoundStartId;
	}

	public static int calculateTotalRounds(int currentTeams) {
		int round = 0;
		while (currentTeams > 1) {
			currentTeams = (currentTeams + 1) / 2;
			round++;
		}
		return round;
	}
}

