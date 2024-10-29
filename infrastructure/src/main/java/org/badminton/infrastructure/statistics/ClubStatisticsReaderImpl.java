package org.badminton.infrastructure.statistics;

import java.util.List;

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
	public ClubStatistics getClubStaticsWithIncrease(String clubToken) {
		ClubStatistics clubStatistics = clubStatisticsRepository.findByClubClubToken(clubToken);
		clubStatistics.increaseVisitedCount();
		return clubStatistics;
	}

	@Override
	public List<Long> findAllClubId() {
		return clubStatisticsRepository.findAllClubId();
	}

	@Override
	public ClubStatistics findByClubId(Long clubId) {
		return clubStatisticsRepository.findByClubClubId(clubId);
	}
}
