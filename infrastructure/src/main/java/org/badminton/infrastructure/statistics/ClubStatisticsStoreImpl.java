package org.badminton.infrastructure.statistics;

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
	public void store(ClubStatistics clubStatistics) {
		clubStatisticsRepository.save(clubStatistics);
	}
}
