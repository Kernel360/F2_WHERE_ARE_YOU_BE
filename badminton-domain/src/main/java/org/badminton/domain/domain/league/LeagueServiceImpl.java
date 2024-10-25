package org.badminton.domain.domain.league;

import java.util.List;

import org.badminton.domain.domain.club.Club;
import org.badminton.domain.domain.club.info.ClubSummaryInfo;
import org.badminton.domain.domain.league.command.LeagueCreateCommand;
import org.badminton.domain.domain.league.command.LeagueCreateNoIncludeClubCommand;
import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
import org.badminton.domain.domain.league.info.LeagueByDateInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.badminton.domain.domain.league.info.LeagueSummaryInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfo;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {

	private final LeagueReader leagueReader;
	private final LeagueStore leagueStore;

	@Override
	public LeagueCreateInfo createLeague(ClubSummaryInfo clubSummaryInfo,
		LeagueCreateNoIncludeClubCommand leagueCreateNoIncludeClubCommand) {
		Club club = clubSummaryInfo.toClub();
		LeagueCreateCommand command = LeagueCreateCommand.build(leagueCreateNoIncludeClubCommand, club);
		return leagueStore.store(command);
	}

	@Override
	public LeagueSummaryInfo getLeague(String clubToken, Long leagueId) {
		var league = leagueReader.readLeague(clubToken, leagueId);
		return LeagueSummaryInfo.toLeagueSummaryInfo(league);
	}

	@Override
	public List<LeagueReadInfo> getLeaguesByMonth(String clubToken, String date) {
		return List.of();
	}

	@Override
	public List<LeagueByDateInfo> getLeaguesByDate(String clubToken, String date) {
		return List.of();
	}

	@Override
	public LeagueUpdateInfo updateLeague(String clubToken, Long leagueId, LeagueUpdateCommand leagueUpdateRequest) {
		return null;
	}

	@Override
	public LeagueCancelInfo cancelLeague(String clubToken, Long leagueId) {
		return null;
	}

}
