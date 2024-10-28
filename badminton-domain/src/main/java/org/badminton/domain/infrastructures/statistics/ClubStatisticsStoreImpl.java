package org.badminton.domain.infrastructures.statistics;

import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.statistics.ClubStatistics;
import org.badminton.domain.domain.statistics.ClubStatisticsStore;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubStatisticsStoreImpl implements ClubStatisticsStore {
	private final ClubStatisticsRepository clubStatisticsRepository;

	@Override
	public void increaseVisitCount(ClubStatistics visitedCount) {
		clubStatisticsRepository.save(visitedCount);
	}

	@Override
	public void store(ClubCreateInfo clubCreateInfo) {
		ClubStatistics clubStatistics = new ClubStatistics(clubCreateInfo);
		clubStatisticsRepository.save(clubStatistics);
	}
}
