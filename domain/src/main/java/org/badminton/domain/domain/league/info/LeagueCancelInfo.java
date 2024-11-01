package org.badminton.domain.domain.league.info;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;

public record LeagueCancelInfo(
	Long leagueId,
	LeagueStatus leagueStatus
) {
	public static LeagueCancelInfo from(League cancelLeague) {
		return new LeagueCancelInfo(
			cancelLeague.getLeagueId(),
			cancelLeague.getLeagueStatus()
		);
	}
}
