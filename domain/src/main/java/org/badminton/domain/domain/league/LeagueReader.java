package org.badminton.domain.domain.league;

import java.time.LocalDateTime;
import java.util.List;

import org.badminton.domain.domain.league.entity.League;

public interface LeagueReader {
	League readLeague(String clubToken, Long leagueId);

	League readLeagueById(Long leagueId);

	List<League> readLeagueByMonth(String clubToken, LocalDateTime startOfMonth, LocalDateTime endOfMonth);

	List<League> readLeagueByDate(String clubToken, LocalDateTime startOfMonth, LocalDateTime endOfMonth);
}
