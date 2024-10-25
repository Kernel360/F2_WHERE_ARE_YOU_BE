package org.badminton.api.application.league;

import java.util.List;

import org.badminton.domain.domain.club.ClubService;
import org.badminton.domain.domain.league.LeagueParticipantService;
import org.badminton.domain.domain.league.LeagueService;
import org.badminton.domain.domain.league.command.LeagueCreateNoIncludeClubCommand;
import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
import org.badminton.domain.domain.league.info.LeagueByDateInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.badminton.domain.domain.league.info.LeagueDetailsInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.badminton.domain.domain.league.info.LeagueSummaryInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfo;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueFacade {
	private final LeagueService leagueService;
	private final LeagueParticipantService leagueParticipantService;
	// private final MatchRetrieveService matchRetrieveService;
	private final ClubService clubService;

	public List<LeagueReadInfo> getLeaguesByMonth(String clubToken, String date) {
		return null;
	}

	public List<LeagueByDateInfo> getLeaguesByDate(String clubToken, String date) {
		return null;
	}

	public LeagueCreateInfo createLeague(String clubToken,
		LeagueCreateNoIncludeClubCommand leagueCreateNoIncludeClubCommand) {
		var clubSummaryInfo = clubService.readClub(clubToken);
		return leagueService.createLeague(clubSummaryInfo, leagueCreateNoIncludeClubCommand);
	}

	public LeagueDetailsInfo getLeague(String clubToken, Long leagueId, String memberToken) {
		LeagueSummaryInfo leagueSummaryInfo = leagueService.getLeague(clubToken, leagueId);
		// TODO : MatchRetrieveService 가 머지 되면 반영하기
		// MatchType matchType = leagueSummaryInfo.matchType();
		//boolean isMatchCreated = MatchRetrieveService.isMatchCreated(matchType, leagueId);
		boolean isMatchCreated = true;
		boolean isParticipatedInLeague = leagueParticipantService.isParticipant(memberToken, leagueId);
		int recruitedMemberCount = leagueParticipantService.countParticipantMember(leagueId);
		return LeagueDetailsInfo.from(leagueSummaryInfo, isMatchCreated, isParticipatedInLeague, recruitedMemberCount);
	}

	public LeagueUpdateInfo updateLeague(String clubToken, Long leagueId, LeagueUpdateCommand leagueUpdateCommand) {
		return null;
	}

	public LeagueCancelInfo cancelLeague(String clubToken, Long leagueId) {
		return null;
	}
}
