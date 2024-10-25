package org.badminton.domain.domain.league.info;

import org.badminton.domain.domain.league.enums.LeagueStatus;

public record LeagueCancelInfo(
	Long leagueId,
	LeagueStatus leagueStatus
) {
}
