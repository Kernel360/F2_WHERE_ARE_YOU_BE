package org.badminton.api.application.club;

import java.util.List;

import org.badminton.domain.domain.club.ClubService;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.statistics.ClubStatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubRankFacade {
	private final ClubService clubService;
	private final ClubStatisticsService clubStatisticsService;

	@Transactional(readOnly = true)
	public List<ClubCardInfo> getTop10PopularClub() {

		return clubStatisticsService.getTop10PopularClub();
	}

	@Transactional(readOnly = true)
	public List<ClubCardInfo> getTop10RecentlyActiveClub() {

		return clubStatisticsService.getTop10RecentlyActiveClub();
	}

	@Transactional(readOnly = true)
	public List<ClubCardInfo> getTop10RecentlyCreatedClub() {
		return clubService.getRecentlyCreatedClub();
	}
}
