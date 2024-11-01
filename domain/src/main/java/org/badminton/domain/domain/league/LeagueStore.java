package org.badminton.domain.domain.league;

import org.badminton.domain.domain.league.entity.League;

public interface LeagueStore {
	League store(League league);
}