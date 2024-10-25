package org.badminton.domain.domain.league;

import org.badminton.domain.domain.league.entity.League;

public interface LeagueReader {
	League readLeague(String clubToken, Long leagueId);
}
