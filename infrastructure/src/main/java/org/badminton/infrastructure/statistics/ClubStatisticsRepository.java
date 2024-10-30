package org.badminton.infrastructure.statistics;

import java.util.List;

import org.badminton.domain.domain.statistics.ClubStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClubStatisticsRepository extends JpaRepository<ClubStatistics, Long> {
	ClubStatistics findByClubClubToken(String clubToken);

	@Query("SELECT c.club.clubId FROM ClubStatistics c")
	List<Long> findAllClubId();

	ClubStatistics findByClubClubId(Long clubId);
}
