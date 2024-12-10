package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;

public record LeagueReadPageInfo(

	Long leagueId,

	String leagueName,

	String description,

	LeagueStatus status,

	LocalDateTime leagueAt,

	String location,

	LocalDateTime modifiedAt,

	String clubName,

	String clubDescription

) {
	public LeagueReadPageInfo(League league) {
		this(
			league.getLeagueId(),
			league.getLeagueName(),
			league.getDescription(),
			league.getLeagueStatus(),
			league.getLeagueAt(),
			league.getAddress().getFullAddress(),
			league.getModifiedAt(),
			league.getClub().getClubName(),
			league.getClub().getClubDescription()
		);
	}

	public static LeagueReadPageInfo from(League league) {
		return new LeagueReadPageInfo(league);
	}

}
