package org.badminton.domain.domain.statistics;

import java.util.List;

import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubStatisticsServiceImpl implements ClubStatisticsService {
	private final ClubStatisticsReader clubStatisticsReader;
	private final ClubStatisticsStore clubStatisticsStore;

	@Override
	public void increaseVisitedClubCount(String clubToken) {
		ClubStatistics increasedResult = clubStatisticsReader.getClubStaticsWithIncrease(clubToken);
		clubStatisticsStore.increaseVisitCount(increasedResult);
	}

	@Override
	public void createStatistic(ClubCreateInfo clubCreateInfo) {
		clubStatisticsStore.store(clubCreateInfo);
	}

	@Override
	public List<Long> clubIdByList() {
		return clubStatisticsReader.findAllClubId();
	}

	@Override
	public void updateByCountAndClubId(Long clubId, int count) {
		var originStatistic = clubStatisticsReader.findByClubId(clubId);
		originStatistic.increaseRegistrationCount(count);
		clubStatisticsStore.update(originStatistic);
	}

	@Override
	public void updateLeagueCountByClubIdAndCount(Long clubId, int count) {
		var originStatistic = clubStatisticsReader.findByClubId(clubId);
		originStatistic.increaseLeagueCount(count);
		clubStatisticsStore.update(originStatistic);
	}
}
