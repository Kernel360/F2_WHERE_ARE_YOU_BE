package org.badminton.domain.infrastructures.statistics;

import org.badminton.domain.domain.statistics.ClubStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubStatisticsRepository extends JpaRepository<ClubStatistics, Long> {
	ClubStatistics findByClubClubToken(String clubToken);
}
