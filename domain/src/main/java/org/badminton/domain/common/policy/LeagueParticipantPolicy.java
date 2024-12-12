package org.badminton.domain.common.policy;

import org.badminton.domain.common.consts.Constants;
import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.league.InvalidDoublesPlayerLimitCountException;
import org.badminton.domain.common.exception.league.InvalidSinglesPlayerLimitCountException;

public class LeagueParticipantPolicy {

	private final static int NO_REMAINDER = 0;

	public static void validatePlayerCount(MatchType matchType, MatchGenerationType matchGenerationType,
		int playerCount) {
		if (matchType == MatchType.SINGLES && matchGenerationType == MatchGenerationType.FREE) {
			validateFreeSinglesPlayerCount(playerCount);
		} else if (matchType == MatchType.SINGLES && matchGenerationType == MatchGenerationType.TOURNAMENT) {
			validateSinglesMinPlayerCount(playerCount);
		} else if (matchType == MatchType.DOUBLES && matchGenerationType == MatchGenerationType.FREE) {
			validateFreeDoublesPlayerCount(playerCount);
		} else if (matchType == MatchType.DOUBLES && matchGenerationType == MatchGenerationType.TOURNAMENT) {
			validateTournamentDoublesPlayerCount(playerCount);
		}
	}

	private static void validateFreeSinglesPlayerCount(int playerCount) {
		validateSinglesMinPlayerCount(playerCount);
		if (playerCount % Constants.FREE_SINGLES_DIVISOR != NO_REMAINDER) {
			throw new InvalidSinglesPlayerLimitCountException(playerCount, MatchType.DOUBLES);
		}
	}

	private static void validateFreeDoublesPlayerCount(int playerCount) {
		validateDoublesMinPlayerCount(playerCount);
		if (playerCount % Constants.FREE_DOUBLES_DIVISOR != NO_REMAINDER) {
			throw new InvalidDoublesPlayerLimitCountException(playerCount);
		}
	}

	private static void validateTournamentDoublesPlayerCount(int playerCount) {
		validateDoublesMinPlayerCount(playerCount);
		if (playerCount % Constants.TOURNAMENT_SINGLES_DIVISOR != NO_REMAINDER) {
			throw new InvalidSinglesPlayerLimitCountException(playerCount, MatchType.DOUBLES);
		}
	}

	private static void validateSinglesMinPlayerCount(int playerCount) {
		if (playerCount < Constants.PLAYER_LIMIT_MIN) {
			throw new InvalidSinglesPlayerLimitCountException(playerCount, MatchType.SINGLES);
		}
	}

	private static void validateDoublesMinPlayerCount(int playerCount) {
		if (playerCount < Constants.DOUBLES_PLAYER_LIMIT_MIN) {
			throw new InvalidDoublesPlayerLimitCountException(playerCount, MatchType.DOUBLES);
		}
	}
}
