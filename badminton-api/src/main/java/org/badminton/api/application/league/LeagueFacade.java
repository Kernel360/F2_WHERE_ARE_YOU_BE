package org.badminton.api.application.league;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.badminton.domain.domain.club.ClubService;
import org.badminton.domain.domain.league.LeagueParticipantService;
import org.badminton.domain.domain.league.LeagueService;
import org.badminton.domain.domain.league.command.LeagueCreateNoIncludeClubCommand;
import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
import org.badminton.domain.domain.league.enums.EndDateType;
import org.badminton.domain.domain.league.enums.StartDateType;
import org.badminton.domain.domain.league.info.LeagueByDateInfo;
import org.badminton.domain.domain.league.info.LeagueByDateInfoWithParticipantCountInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.badminton.domain.domain.league.info.LeagueDetailsInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.badminton.domain.domain.league.info.LeagueSummaryInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfoWithParticipantCountInfo;
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
		return leagueService.getLeaguesByMonth(clubToken, date);
	}

	public List<LeagueByDateInfoWithParticipantCountInfo> getLeaguesByDate(String clubToken, String date) {
		List<LeagueByDateInfo> leagueByDateInfoList = leagueService.getLeaguesByDate(clubToken, date);
		return leagueByDateInfoList.stream().map((leagueByDateInfo -> {
			int participantCount = leagueParticipantService.countParticipantMember(leagueByDateInfo.leagueId());
			return new LeagueByDateInfoWithParticipantCountInfo(leagueByDateInfo, participantCount);
		})).collect(Collectors.toList());
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
		// boolean isMatchCreated = MatchRetrieveService.isMatchCreated(matchType, leagueId);
		boolean isMatchCreated = true;
		boolean isParticipatedInLeague = leagueParticipantService.isParticipant(memberToken, leagueId);
		int recruitedMemberCount = leagueParticipantService.countParticipantMember(leagueId);
		return LeagueDetailsInfo.from(leagueSummaryInfo, isMatchCreated, isParticipatedInLeague, recruitedMemberCount);
	}

	public LeagueUpdateInfoWithParticipantCountInfo updateLeague(String clubToken, Long leagueId,
		LeagueUpdateCommand leagueUpdateCommand) {
		var origin = leagueService.getLeagueDetail(clubToken, leagueId);
		var updateLeague = leagueService.updateLeague(origin, leagueUpdateCommand);
		int participantCount = leagueParticipantService.countParticipantMember(leagueId);
		return LeagueUpdateInfoWithParticipantCountInfo
			.leagueByDateInfoWithParticipantCountInfo(updateLeague, participantCount);

	}

	public LeagueCancelInfo cancelLeague(String clubToken, Long leagueId) {
		return leagueService.cancelLeague(clubToken, leagueId);
	}

	private LocalDate parseDateByMonth(String date) {
		String[] parts = date.split("-");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		return LocalDate.of(year, month, StartDateType.START_DAY.getDescription()); // 첫 번째 날로 초기화
	}

	private LocalDateTime getStartOfMonth(LocalDate date) {
		return LocalDateTime.of(date.getYear(), date.getMonthValue(), StartDateType.START_DAY.getDescription(),
			StartDateType.START_HOUR.getDescription(), StartDateType.START_MINUTE.getDescription());
	}

	private LocalDateTime getEndOfMonth(LocalDate date) {
		return LocalDateTime.of(date.getYear(), date.getMonthValue(),
			date.lengthOfMonth(), EndDateType.END_HOUR.getDescription(), EndDateType.END_MINUTE.getDescription());
	}
}
