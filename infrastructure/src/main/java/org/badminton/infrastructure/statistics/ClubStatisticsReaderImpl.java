package org.badminton.infrastructure.statistics;

import java.util.List;

import org.badminton.domain.domain.statistics.ClubStatistics;
import org.badminton.domain.domain.statistics.ClubStatisticsReader;
import org.badminton.domain.domain.statistics.ClubStatisticsRepositoryCustom;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubStatisticsReaderImpl implements ClubStatisticsReader {
	private final ClubStatisticsRepository clubStatisticsRepository;
	private final ClubStatisticsRepositoryCustom clubStatisticsRepositoryCustom;

	@Override
	public ClubStatistics getClubStaticsWithIncrease(String clubToken) {
		ClubStatistics clubStatistics = clubStatisticsRepository.findByClubClubToken(clubToken);
		clubStatistics.increaseVisitedCount();
		return clubStatistics;
	}

	@Override
	public List<Long> findAllClubId() {
		return clubStatisticsRepositoryCustom.findAllClubId();
	}

	@Override
	public ClubStatistics findByClubId(Long clubId) {
		return clubStatisticsRepository.findByClubClubId(clubId);
	}

	@Override
	public List<ClubStatistics> findAll() {
		return clubStatisticsRepository.findAll();
	}

	@Override
	public List<ClubStatistics> readTop10PopularClubStatistics() {
		return clubStatisticsRepository.findTop10ByOrderByPopularityScoreDesc();
	}

	@Override
	public List<ClubStatistics> readTop10RecentlyActiveClubStatistics() {
		return clubStatisticsRepository.findTop10ByOrderByActivityScoreDesc();
	}
}
