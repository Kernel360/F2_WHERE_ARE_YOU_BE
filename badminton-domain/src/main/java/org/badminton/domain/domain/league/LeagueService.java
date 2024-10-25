package org.badminton.domain.domain.league;

import java.util.List;

import org.badminton.domain.domain.club.info.ClubSummaryInfo;
import org.badminton.domain.domain.league.command.LeagueCreateNoIncludeClubCommand;
import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
import org.badminton.domain.domain.league.info.LeagueByDateInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.badminton.domain.domain.league.info.LeagueSummaryInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfo;

public interface LeagueService {

	List<LeagueReadInfo> getLeaguesByMonth(String clubToken, String date);

	List<LeagueByDateInfo> getLeaguesByDate(String clubToken, String date);

	LeagueUpdateInfo updateLeague(String clubToken, Long leagueId, LeagueUpdateCommand leagueUpdateRequest);

	LeagueCancelInfo cancelLeague(String clubToken, Long leagueId);

	LeagueCreateInfo createLeague(ClubSummaryInfo clubSummaryInfo,
		LeagueCreateNoIncludeClubCommand leagueCreateNoIncludeClubCommand);

	LeagueSummaryInfo getLeague(String clubToken, Long leagueId);
}
