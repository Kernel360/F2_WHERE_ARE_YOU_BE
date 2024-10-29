package org.badminton.infrastructure.statistics;

import org.badminton.domain.domain.statistics.ClubStatistics;
import org.badminton.domain.domain.statistics.ClubStatisticsReader;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubStatisticsReaderImpl implements ClubStatisticsReader {
	private final ClubStatisticsRepository clubStatisticsRepository;

	@Override
	public int countVisited(String clubToken) {
		return 0;
	}

	@Override
	public ClubStatistics getClubStaticsWithIncrease(String clubToken) {
		ClubStatistics clubStatistics = clubStatisticsRepository.findByClubClubToken(clubToken);
		clubStatistics.increaseVisitedCount();
		return clubStatistics;
	}
}
