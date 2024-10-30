package org.badminton.domain.domain.league;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.badminton.domain.common.exception.league.OngoingAndUpcomingLeagueCanNotBePastException;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.info.ClubSummaryInfo;
import org.badminton.domain.domain.league.command.LeagueCancelCommand;
import org.badminton.domain.domain.league.command.LeagueCreateCommand;
import org.badminton.domain.domain.league.command.LeagueCreateNoIncludeClubCommand;
import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
import org.badminton.domain.domain.league.command.LeagueUpdatedCommand;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.EndDateType;
import org.badminton.domain.domain.league.enums.StartDateType;
import org.badminton.domain.domain.league.info.LeagueByDateInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.badminton.domain.domain.league.info.LeagueDetailInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.badminton.domain.domain.league.info.LeagueSummaryInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfo;
import org.badminton.domain.domain.league.info.OngoingAndUpcomingLeagueInfo;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {

    private final LeagueReader leagueReader;
    private final LeagueStore leagueStore;
    private final LeagueParticipantReader leagueParticipantReader;

	@Override
	@Transactional
	public LeagueCreateInfo createLeague(ClubSummaryInfo clubSummaryInfo,
		LeagueCreateNoIncludeClubCommand leagueCreateNoIncludeClubCommand) {
		Club club = clubSummaryInfo.toClub();
		LeagueCreateCommand command = LeagueCreateCommand.build(leagueCreateNoIncludeClubCommand, club);
		return leagueStore.store(command);
	}

	@Override
	@Transactional
	public LeagueSummaryInfo getLeague(String clubToken, Long leagueId) {
		var league = leagueReader.readLeague(clubToken, leagueId);
		return LeagueSummaryInfo.toLeagueSummaryInfo(league);
	}

	@Override
	@Transactional
	public LeagueDetailInfo getLeagueDetail(String clubToken, Long leagueId) {
		var league = leagueReader.readLeague(clubToken, leagueId);
		return LeagueDetailInfo.toLeagueDetailInfo(league);
	}

	@Override
	@Transactional
	public LeagueUpdateInfo updateLeague(LeagueDetailInfo origin, LeagueUpdateCommand leagueUpdateCommand) {
		LeagueUpdatedCommand command = leagueUpdateCommand.toUpdate(origin);
		return leagueStore.update(command);
	}

	@Override
	public Integer getLeagueCountByClubId(Long clubId) {
		return leagueReader.getCountByClubId(clubId);
	}

	@Override
	public List<LeagueReadInfo> getLeaguesByMonth(String clubToken, String date) {
		LocalDate parsedDate = parseDateByMonth(date);
		LocalDateTime startOfMonth = getStartOfMonth(parsedDate);
		LocalDateTime endOfMonth = getEndOfMonth(parsedDate);
		List<League> result =
			leagueReader.readLeagueByMonth(clubToken, startOfMonth, endOfMonth);
		return result.stream()
			.map(LeagueReadInfo::leagueReadEntityToInfo)
			.collect(
				Collectors.toList());
	}

    @Override
    public List<LeagueByDateInfo> getLeaguesByDate(String clubToken, String date) {
        LocalDate parsedDate = parseDateByMonth(date);
        LocalDateTime startOfMonth = getStartOfMonth(parsedDate);
        LocalDateTime endOfMonth = getEndOfMonth(parsedDate);
        List<League> result =
                leagueReader.readLeagueByDate(clubToken, startOfMonth, endOfMonth);
        return result.stream()
                .map(LeagueByDateInfo::leagueByDateEntityToInfo)
                .collect(
                        Collectors.toList());
    }

    @Override
    public List<OngoingAndUpcomingLeagueInfo> getOngoingAndUpcomingLeaguesByDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new OngoingAndUpcomingLeagueCanNotBePastException(date, LocalDate.now());
        }
        List<League> leagues = leagueReader.readOngoingAndUpcomingLeagueByDate(date);
        return leagues.stream()
                .map(league -> OngoingAndUpcomingLeagueInfo.from(league,
                        leagueParticipantReader.countParticipantMember(league.getLeagueId())))
                .toList();
    }

    @Override
    public LeagueCancelInfo cancelLeague(String clubToken, Long leagueId) {
        var league = leagueReader.readLeague(clubToken, leagueId);
        league.cancelLeague();
        var command = LeagueCancelCommand.toCommand(league);
        return leagueStore.cancelLeague(command);
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

	private LocalDate parseDateByDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);
	}

	private LocalDateTime getStartOfDay(LocalDate date) {
		return date.atStartOfDay();
	}

	private LocalDateTime getEndOfDay(LocalDate date) {
		return date.atTime(23, 59, 59);
	}

}
