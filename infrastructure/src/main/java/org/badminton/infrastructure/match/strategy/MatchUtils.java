package org.badminton.infrastructure.match.strategy;

import org.springframework.stereotype.Component;

@Component
public class MatchUtils {

	public static Integer calculateNextRoundMatchId(int currentMatchId, int totalParticipants, int startMatchId) {

		int totalRounds = calculateTotalRounds(totalParticipants); // 총 라운드의 개수
		int matchesInCurrentLevel = totalParticipants / 2; // 현재 레벨의 매치 수 -> 참여자 인원 /2
		int currentRoundOffset = startMatchId; // 현재 라운드의 시작 매치 ID

		for (int level = 1; level <= totalRounds; level++) {
			if (isCurrentMatchInRound(currentMatchId, currentRoundOffset,
				matchesInCurrentLevel)) { // 현재 라운드에 속하는지 확인 -> 현재 match 의 Id가 현재 라운드의 시작 매치 ID + 매치 수의 값보다 작으면 속하는거
				return calculateNextMatchIdInRound(currentMatchId, currentRoundOffset, matchesInCurrentLevel);
			}
			currentRoundOffset += matchesInCurrentLevel;
			matchesInCurrentLevel /= 2;
		}
		return null; // 결승전 이후에는 다음 MATCH 가 X -> null 반환
	}

	private static int calculateNextMatchIdInRound(int currentMatchId, int currentRoundOffset,
		int matchesInCurrentLevel) {
		int matchIndexInLevel = currentMatchId - currentRoundOffset; // 속한다면 -> 현재 매치의 인덱스를 구하기 (시작 id를 기준으로)
		int nextMatchIndexInLevel = matchIndexInLevel / 2;
		int nextRoundOffset = currentRoundOffset + matchesInCurrentLevel; // 현재 매치의 인덱스 + 매치 수 -> 다음 라운드의 첫번째 매치의 ID
		return nextRoundOffset
			+ nextMatchIndexInLevel;
	}

	private static boolean isCurrentMatchInRound(int currentMatchId, int currentRoundOffset,
		int matchesInCurrentLevel) {
		return currentMatchId < currentRoundOffset
			+ matchesInCurrentLevel;
	}

	public static int calculateTotalRounds(int currentTeams) {
		return (int)(Math.log(currentTeams) / Math.log(2)); // Math.log2(currentTeams)
	}

}
