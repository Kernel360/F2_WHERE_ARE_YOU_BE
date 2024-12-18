package org.badminton.infrastructure.league;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.exception.league.LeagueAlreadyCanceledException;
import org.badminton.domain.common.exception.league.LeagueCreationWithin3HoursException;
import org.badminton.domain.common.exception.league.LeagueNotExistException;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.AllowedLeagueStatus;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.league.enums.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueReaderImpl implements LeagueReader {
	private final LeagueRepository leagueRepository;

	@Override
	public League readLeague(String clubToken, Long leagueId) {
		return leagueRepository.findByClubClubTokenAndLeagueId(clubToken, leagueId).orElseThrow(
			() -> new LeagueNotExistException(clubToken, leagueId));
	}

	@Override
	public League readLeagueNotCanceled(Long leagueId) {
		return leagueRepository.findByLeagueIdAndLeagueStatusNotContaining(leagueId, LeagueStatus.CANCELED)
			.orElseThrow(() -> new LeagueAlreadyCanceledException(leagueId));
	}

	@Override
	public League readLeagueById(Long leagueId) {
		return leagueRepository.findById(leagueId)
			.orElseThrow(() -> new LeagueNotExistException(leagueId));
	}

	@Override
	public List<League> readLeagueByMonth(String clubToken, LocalDateTime startOfMonth, LocalDateTime endOfMonth) {
		return leagueRepository.findAllByClubClubTokenAndLeagueAtBetween(clubToken, startOfMonth, endOfMonth);
	}

	@Override
	public List<League> readLeagueByDate(String clubToken, LocalDateTime startOfMonth, LocalDateTime endOfMonth) {
		return leagueRepository.findAllByClubClubTokenAndLeagueAtBetween(clubToken, startOfMonth, endOfMonth);
	}

	@Override
	public Integer getCountByClubId(Long clubId) {
		return leagueRepository.countByClubClubIdAndLeagueStatus(clubId, LeagueStatus.RECRUITING_COMPLETED);
	}

	@Override
	public MatchGenerationType getMatchGenerationTypeByLeagueId(Long leagueId) {
		return leagueRepository.getMatchGenerationTypeByLeagueId(leagueId).orElseThrow(
			() -> new LeagueNotExistException(leagueId)
		);
	}

	@Override
	public void checkLeagueExistIn3Hours(String memberToken, LocalDateTime leagueAt) {
		// 3시간 이내에 경기를 생성했는지 확인하는 로직
		LocalDateTime endTime = leagueAt.plusHours(3);
		boolean exists = leagueRepository
			.existsByLeagueOwnerMemberTokenAndLeagueAtBetween(memberToken, leagueAt, endTime);
		if (exists) {
			throw new LeagueCreationWithin3HoursException(leagueAt);
		}

	}

	@Override
	public Page<League> readLeagueByPageable(Pageable pageable) {
		return leagueRepository.findAllByLeagueStatusNot(LeagueStatus.CANCELED, pageable);
	}

	@Override
	public Page<League> readLeagueStatusIsNotAllAndRegionIsNotAll(AllowedLeagueStatus leagueStatus, Region region,
		LocalDate date, Pageable pageable) {
		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = date.atTime(23, 59, 59);

		return leagueRepository.findAllByLeagueAtBetweenAndLeagueStatusAndAddressRegion(
			startOfDay,
			endOfDay,
			leagueStatus.getStatus(),
			Region.getNameByCode(region.name()),
			pageable
		);
	}

	@Override
	public Page<League> readLeagueStatusIsAllAndRegionIsNotAll(Region region, LocalDate date, Pageable pageable) {
		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = date.atTime(23, 59, 59);
		return leagueRepository.findAllByLeagueAtBetweenAndAddressRegion(
			startOfDay,
			endOfDay,
			Region.getNameByCode(region.name()),
			pageable
		);
	}

	@Override
	public Page<League> readLeagueStatusIsNotAllAndRegionIsAll(AllowedLeagueStatus leagueStatus, LocalDate date,
		Pageable pageable) {
		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = date.atTime(23, 59, 59);
		return leagueRepository.findAllByLeagueAtBetweenAndLeagueStatus(
			startOfDay,
			endOfDay,
			leagueStatus.getStatus(),
			pageable
		);
	}

	@Override
	public Page<League> readLeagueStatusIsAllAndRegionIsAll(LocalDate date, Pageable pageable) {
		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = date.atTime(23, 59, 59);
		return leagueRepository.findAllByLeagueAtBetween(
			startOfDay,
			endOfDay,
			pageable
		);
	}
}
