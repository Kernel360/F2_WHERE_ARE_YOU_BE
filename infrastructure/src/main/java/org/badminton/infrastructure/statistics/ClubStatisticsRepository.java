package org.badminton.infrastructure.statistics;

import org.badminton.domain.domain.statistics.ClubStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubStatisticsRepository extends JpaRepository<ClubStatistics, Long> {
	ClubStatistics findByClubClubToken(String clubToken);

	ClubStatistics findByClubClubId(Long clubId);
}
