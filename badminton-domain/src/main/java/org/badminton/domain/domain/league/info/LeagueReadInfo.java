package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.enums.LeagueStatus;

public record LeagueReadInfo(
	Long leagueId,

	String leagueName,

	LeagueStatus status,

	LocalDateTime leagueAt,

	int playerCount
) {
}
