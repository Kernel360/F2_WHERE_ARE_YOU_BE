package org.badminton.domain.domain.statistics;

import java.util.List;

import org.badminton.domain.domain.club.ClubPage;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.club.vo.ClubCache;
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
		ClubStatistics clubStatistics = clubStatisticsReader.readClubStatistics(clubToken);
		clubStatistics.increaseVisitedCount();
		clubStatisticsStore.store(clubStatistics);
		log.info("**** 스레드 이름 : {}", Thread.currentThread().getName());
		log.info("***** club visit count : {}", clubStatistics.getVisitedCount());
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

		List<ClubCache> clubCaches = clubStatisticsReader.readTop10PopularClub();
		var response = ClubPage.builder().clubCacheList(clubCaches).build();
		return response.clubListToRedisPageCardInfo();
	}

	@Override
	@Transactional
	public List<ClubCardInfo> getTop10RecentlyActiveClub() {

		List<ClubCache> clubCaches = clubStatisticsReader.readTop10RecentlyActiveClub();
		var response = ClubPage.builder().clubCacheList(clubCaches).build();
		return response.clubListToRedisPageCardInfo();
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
