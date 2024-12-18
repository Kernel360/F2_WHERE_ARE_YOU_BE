package org.badminton.domain.domain.statistics;

import java.util.List;

import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubStatisticsServiceImpl implements ClubStatisticsService {
	private final ClubStatisticsReader clubStatisticsReader;
	private final ClubStatisticsStore clubStatisticsStore;

	@Override
	@Transactional
	public void increaseVisitedClubCount(String clubToken) {
		clubStatisticsStore.increaseClubVisitCount(clubToken);
	}

	@Override
	@Transactional
	public void createStatistic(ClubCreateInfo clubCreateInfo) {
		ClubStatistics clubStatistics = new ClubStatistics(clubCreateInfo);
		clubStatisticsStore.store(clubStatistics);
	}

	@Override
	@Transactional
	public List<Long> clubIdByList() {
		return clubStatisticsReader.findAllClubId();
	}

	@Override
	@Transactional
	public void updateByCountAndClubId(Long clubId, int count) {
		var originStatistic = clubStatisticsReader.findByClubId(clubId);
		originStatistic.increaseRegistrationCount(count);
		clubStatisticsStore.store(originStatistic);
	}

	@Override
	@Transactional
	public List<ClubCardInfo> getTop10PopularClub() {

		return clubStatisticsReader.readTop10PopularClub();

	}

	@Override
	@Transactional
	public List<ClubCardInfo> getTop10RecentlyActiveClub() {

		return clubStatisticsReader.readTop10RecentlyActiveClub();
	}

	@Override
	@Transactional
	public void refreshPopularClubsCache() {
		clubStatisticsReader.refreshTop10PopularClubsCache();
	}

	@Override
	@Transactional
	public void refreshRecentlyActivityClubsCache() {
		clubStatisticsReader.refreshActivityClubsCache();
	}

	@Override
	@Transactional
	public void updateLeagueCountByClubIdAndCount(Long clubId, int count) {
		var originStatistic = clubStatisticsReader.findByClubId(clubId);
		originStatistic.increaseLeagueCount(count);
		clubStatisticsStore.store(originStatistic);
	}
}
