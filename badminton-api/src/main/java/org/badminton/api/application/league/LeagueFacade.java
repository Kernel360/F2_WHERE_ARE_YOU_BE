package org.badminton.api.application.league;

import java.util.List;

import org.badminton.domain.domain.league.command.LeagueCreateCommand;
import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
import org.badminton.domain.domain.league.info.LeagueByDateInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.badminton.domain.domain.league.info.LeagueDetailsInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfo;

import jakarta.validation.Valid;

public class LeagueFacade {
	public List<LeagueReadInfo> getLeaguesByMonth(String clubToken, String date) {
		return null;
	}

	public List<LeagueByDateInfo> getLeaguesByDate(String clubToken, String date) {
		return null;
	}

	public LeagueCreateInfo createLeague(String clubToken, @Valid LeagueCreateCommand leagueCreateCommand) {
		return null;
	}

	public LeagueDetailsInfo getLeague(String clubToken, String leagueId, String memberToken) {
		return null;
	}

	public LeagueUpdateInfo updateLeague(String clubToken, Long leagueId,
		@Valid LeagueUpdateCommand leagueUpdateCommand) {
		return null;
	}

	public LeagueCancelInfo cancelLeague(String clubToken, Long leagueId) {
		return null;
	}
}
