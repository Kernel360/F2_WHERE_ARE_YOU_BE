package org.badminton.domain.domain.league;

import java.time.LocalDate;
import java.util.List;

import org.badminton.domain.domain.league.command.LeagueCreateNoIncludeClubCommand;
import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
import org.badminton.domain.domain.league.enums.AllowedLeagueStatus;
import org.badminton.domain.domain.league.enums.Region;
import org.badminton.domain.domain.league.info.LeagueByDateInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.badminton.domain.domain.league.info.LeagueDetailInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.badminton.domain.domain.league.info.LeagueReadPageInfo;
import org.badminton.domain.domain.league.info.LeagueRecruitingCompleteInfo;
import org.badminton.domain.domain.league.info.LeagueSummaryInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfo;
import org.badminton.domain.domain.league.info.OngoingAndUpcomingLeagueInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeagueService {

	List<LeagueReadInfo> getLeaguesByMonth(String clubToken, String date);

	List<LeagueByDateInfo> getLeaguesByDate(String clubToken, String date);

	Page<OngoingAndUpcomingLeagueInfo> getOngoingAndUpcomingLeaguesByDate(AllowedLeagueStatus leagueStatus,
		Region region,
		LocalDate localDate, Pageable pageable);

	LeagueCancelInfo cancelLeague(String clubToken, Long leagueId, String memberToken);

	LeagueCreateInfo createLeague(String memberToken, String clubToken,
		LeagueCreateNoIncludeClubCommand leagueCreateNoIncludeClubCommand);

	LeagueSummaryInfo getLeague(String clubToken, Long leagueId);

	LeagueDetailInfo getLeagueDetail(String clubToken, Long leagueId);

	Integer getLeagueCountByClubId(Long clubId);

	LeagueUpdateInfo updateLeague(String clubToken, Long leagueId, LeagueUpdateCommand leagueUpdateCommand,
		String memberToken);

	LeagueRecruitingCompleteInfo completeLeagueRecruiting(String clubToken, Long leagueId, String memberToken);

	List<LeagueReadPageInfo> getLeaguePageable(Pageable pageable);
}
