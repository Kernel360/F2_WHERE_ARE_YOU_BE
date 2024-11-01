package org.badminton.domain.domain.league.command;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;

public record LeagueUpdateCommand(
	String leagueName,

	String description,

	int playerLimitCount,

	MatchType matchType,

	MatchGenerationType matchGenerationType
) {
}
