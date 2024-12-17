package org.badminton.infrastructure.statistics;

import java.util.List;

import org.badminton.domain.domain.statistics.ClubStatistics;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;

public interface ClubStatisticsRepository extends JpaRepository<ClubStatistics, Long> {

	@Query("SELECT cs FROM ClubStatistics cs JOIN FETCH cs.club WHERE cs.club.clubToken = :clubToken")
	ClubStatistics findByClubClubToken(@Param("clubToken") String clubToken);

	ClubStatistics findByClubClubId(Long clubId);

	@Query("SELECT DISTINCT cs " +
		"FROM ClubStatistics cs " +
		"JOIN FETCH cs.club c " +
		"LEFT JOIN FETCH c.clubMembers cm " +
		"LEFT JOIN FETCH cm.member m " +
		"ORDER BY cs.popularityScore DESC")
	List<ClubStatistics> findTopPopularClubs(Pageable pageable);

	@Query("SELECT DISTINCT cs " +
		"FROM ClubStatistics cs " +
		"JOIN FETCH cs.club c " +
		"LEFT JOIN FETCH c.clubMembers cm " +
		"LEFT JOIN FETCH cm.member m " +
		"ORDER BY cs.activityScore DESC")
	List<ClubStatistics> findTop10ByActivityClubs(Pageable pageable);

}
