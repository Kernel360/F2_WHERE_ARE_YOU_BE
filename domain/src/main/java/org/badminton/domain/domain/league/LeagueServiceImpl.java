package org.badminton.domain.domain.league;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.badminton.domain.common.exception.league.LeagueCannotBeCanceled;
import org.badminton.domain.common.exception.league.LeagueCannotBeUpdated;
import org.badminton.domain.common.exception.league.NotLeagueOwnerException;
import org.badminton.domain.common.exception.league.OngoingAndUpcomingLeagueCanNotBePastException;
import org.badminton.domain.domain.club.ClubReader;
import org.badminton.domain.domain.league.command.LeagueCreateCommand;
import org.badminton.domain.domain.league.command.LeagueCreateNoIncludeClubCommand;
import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.AllowedLeagueStatus;
import org.badminton.domain.domain.league.enums.EndDateType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.league.enums.Region;
import org.badminton.domain.domain.league.enums.StartDateType;
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
import org.badminton.domain.domain.league.vo.LeagueSearchStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {

	private final LeagueReader leagueReader;
	private final LeagueStore leagueStore;
	private final LeagueParticipantReader leagueParticipantReader;
	private final ClubReader clubReader;
	private final LeagueSearchStrategy leagueSearchStrategy;

	@Override
	@Transactional
	public LeagueCreateInfo createLeague(String memberToken, String clubToken,
		LeagueCreateNoIncludeClubCommand leagueCreateNoIncludeClubCommand) {
		var club = clubReader.readClub(clubToken);
		var createdLeague = LeagueCreateCommand.build(leagueCreateNoIncludeClubCommand, memberToken, club).toEntity();
		leagueReader.checkLeagueExistIn3Hours(memberToken, createdLeague.getLeagueAt());
		leagueStore.store(createdLeague);
		return LeagueCreateInfo.from(createdLeague);
	}

	@Override
	@Transactional(readOnly = true)
	public LeagueSummaryInfo getLeague(String clubToken, Long leagueId) {
		var league = leagueReader.readLeague(clubToken, leagueId);
		return LeagueSummaryInfo.from(league);
	}

	@Override
	@Transactional(readOnly = true)
	public LeagueDetailInfo getLeagueDetail(String clubToken, Long leagueId) {
		var league = leagueReader.readLeague(clubToken, leagueId);
		return LeagueDetailInfo.from(league);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getLeagueCountByClubId(Long clubId) {
		return leagueReader.getCountByClubId(clubId);
	}

	@Override
	@Transactional
	public LeagueUpdateInfo updateLeague(String clubToken, Long leagueId, LeagueUpdateCommand leagueUpdateCommand,
		String memberToken) {
		League league = leagueReader.readLeague(clubToken, leagueId);
		validateLeagueOwner(memberToken, league);

		if (!(league.getLeagueStatus() == LeagueStatus.RECRUITING
			|| league.getLeagueStatus() == LeagueStatus.RECRUITING_COMPLETED)) {
			throw new LeagueCannotBeUpdated(leagueId, league.getLeagueStatus());
		}
		league.updateLeague(leagueUpdateCommand.leagueName(), leagueUpdateCommand.description(),
			leagueUpdateCommand.playerLimitCount(), leagueUpdateCommand.matchType(),
			leagueUpdateCommand.matchGenerationType());
		return LeagueUpdateInfo.from(leagueStore.store(league));
	}

	@Override
	@Transactional
	public LeagueRecruitingCompleteInfo completeLeagueRecruiting(String clubToken, Long leagueId, String memberToken) {
		League league = leagueReader.readLeague(clubToken, leagueId);
		validateLeagueOwner(memberToken, league);
		league.completeLeagueRecruiting(leagueParticipantReader.countParticipantMember(leagueId));
		leagueStore.store(league);
		return new LeagueRecruitingCompleteInfo(league.getLeagueId(), league.getLeagueStatus());
	}

	@Override
	@Transactional(readOnly = true)
	public List<LeagueReadPageInfo> getLeaguePageable(Pageable pageable) {
		Page<League> leagues = leagueReader.readLeagueByPageable(pageable);
		return leagues.stream().map(LeagueReadPageInfo::from).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<LeagueReadInfo> getLeaguesByMonth(String clubToken, String date) {
		LocalDate parsedDate = parseDateByMonth(date);
		LocalDateTime startOfMonth = getStartOfMonth(parsedDate);
		LocalDateTime endOfMonth = getEndOfMonth(parsedDate);
		List<League> result =
			leagueReader.readLeagueByMonth(clubToken, startOfMonth, endOfMonth);
		return result.stream()
			.map(LeagueReadInfo::from)
			.collect(
				Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<LeagueByDateInfo> getLeaguesByDate(String clubToken, String date) {
		LocalDate parsedDate = parseDate(date);
		LocalDateTime startOfDay = getStartOfDay(parsedDate);
		LocalDateTime endOfMDay = getEndOfDay(parsedDate);
		List<League> result =
			leagueReader.readLeagueByDate(clubToken, startOfDay, endOfMDay);
		return result.stream()
			.map(LeagueByDateInfo::leagueByDateEntityToInfo)
			.collect(
				Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<OngoingAndUpcomingLeagueInfo> getOngoingAndUpcomingLeaguesByDate(
		AllowedLeagueStatus leagueStatus,
		Region region,
		LocalDate date,
		Pageable pageable
	) {
		if (date.isBefore(LocalDate.now())) {
			throw new OngoingAndUpcomingLeagueCanNotBePastException(date, LocalDate.now());
		}
		Page<League> leagues = leagueSearchStrategy.getStrategy(leagueStatus, region, date, pageable);
		return leagues.map(league -> OngoingAndUpcomingLeagueInfo.from(
			league, leagueParticipantReader.countParticipantMember(league.getLeagueId())));
	}

	@Override
	@Transactional
	public LeagueCancelInfo cancelLeague(String clubToken, Long leagueId, String memberToken) {
		var league = leagueReader.readLeague(clubToken, leagueId);
		validateLeagueOwner(memberToken, league);
		if (LocalDateTime.now().isAfter(league.getLeagueAt())) {
			throw new LeagueCannotBeCanceled(leagueId, league.getLeagueAt());
		}
		league.cancelLeague();
		leagueStore.store(league);
		return LeagueCancelInfo.from(league);
	}

	private void validateLeagueOwner(String memberToken, League league) {
		if (!league.getLeagueOwnerMemberToken().equals(memberToken)) {
			throw new NotLeagueOwnerException(memberToken);
		}
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

	private LocalDate parseDate(String date) {
		String[] parts = date.split("-");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		int day = Integer.parseInt(parts[2]);
		return LocalDate.of(year, month, day); // 첫 번째 날로 초기화
	}

	private LocalDateTime getStartOfDay(LocalDate date) {
		return LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
			StartDateType.START_HOUR.getDescription(), StartDateType.START_MINUTE.getDescription());
	}

	private LocalDateTime getEndOfDay(LocalDate date) {
		return LocalDateTime.of(date.getYear(), date.getMonthValue(),
			date.getDayOfMonth(), EndDateType.END_HOUR.getDescription(), EndDateType.END_MINUTE.getDescription());
	}

}
