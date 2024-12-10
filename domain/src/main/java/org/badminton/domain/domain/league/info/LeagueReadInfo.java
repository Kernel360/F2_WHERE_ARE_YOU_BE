package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;

public record LeagueReadInfo(
	Long leagueId,

	String leagueName,

	LeagueStatus status,

	LocalDateTime leagueAt,

	int playerCount
) {
	public LeagueReadInfo(League entity) {
		this(
			entity.getLeagueId(),
			entity.getLeagueName(),
			entity.getLeagueStatus(),
			entity.getLeagueAt(),
			entity.getPlayerLimitCount()
		);
	}

	public static LeagueReadInfo from(League entity) {
		return new LeagueReadInfo(entity);
	}
}
