package org.badminton.infrastructure.statistics;

import java.util.List;

import org.badminton.domain.domain.statistics.ClubStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubStatisticsRepository extends JpaRepository<ClubStatistics, Long> {
	ClubStatistics findByClubClubToken(String clubToken);

	ClubStatistics findByClubClubId(Long clubId);

	List<ClubStatistics> findTop10ByOrderByPopularityScoreDesc();

	List<ClubStatistics> findTop10ByOrderByActivityScoreDesc();
}
