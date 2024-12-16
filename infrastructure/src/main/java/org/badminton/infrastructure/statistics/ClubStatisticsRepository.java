package org.badminton.infrastructure.statistics;

import java.util.List;

import org.badminton.domain.domain.statistics.ClubStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;

public interface ClubStatisticsRepository extends JpaRepository<ClubStatistics, Long> {

	@Query("SELECT cs FROM ClubStatistics cs JOIN FETCH cs.club WHERE cs.club.clubToken = :clubToken")
	ClubStatistics findByClubClubToken(@Param("clubToken") String clubToken);

	ClubStatistics findByClubClubId(Long clubId);

	List<ClubStatistics> findTop10ByOrderByPopularityScoreDesc();

	List<ClubStatistics> findTop10ByOrderByActivityScoreDesc();
}
