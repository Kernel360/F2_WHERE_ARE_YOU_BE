package org.badminton.domain.domain.league;

import org.badminton.domain.domain.league.command.LeagueCancelCommand;
import org.badminton.domain.domain.league.command.LeagueCreateCommand;
import org.badminton.domain.domain.league.command.LeagueUpdatedCommand;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfo;

public interface LeagueStore {
	LeagueCreateInfo store(LeagueCreateCommand command);

	LeagueUpdateInfo update(LeagueUpdatedCommand command);

	LeagueCancelInfo cancelLeague(LeagueCancelCommand command);
}
