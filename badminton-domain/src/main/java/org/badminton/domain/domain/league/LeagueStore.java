package org.badminton.domain.domain.league;

import org.badminton.domain.domain.league.command.LeagueCreateCommand;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;

public interface LeagueStore {
	LeagueCreateInfo store(LeagueCreateCommand command);
}
