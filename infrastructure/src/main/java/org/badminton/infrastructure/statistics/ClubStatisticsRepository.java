package org.badminton.infrastructure.statistics;

import java.util.List;

import org.badminton.domain.domain.club.vo.ClubCache;
import org.badminton.domain.domain.statistics.ClubStatistics;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;

public interface ClubStatisticsRepository extends JpaRepository<ClubStatistics, Long> {

	@Query("SELECT cs FROM ClubStatistics cs JOIN FETCH cs.club WHERE cs.club.clubToken = :clubToken")
	ClubStatistics findByClubClubToken(@Param("clubToken") String clubToken);

	ClubStatistics findByClubClubId(Long clubId);

	@Query("""
		    SELECT new org.badminton.domain.domain.club.vo.ClubCache(
		        c.clubToken, c.clubName, c.clubDescription, c.clubImage, c.createdAt, c.modifiedAt,
		        SUM(CASE WHEN cm.member.tier = 'GOLD' THEN 1 ELSE 0 END),
		        SUM(CASE WHEN cm.member.tier = 'SILVER' THEN 1 ELSE 0 END),
		        SUM(CASE WHEN cm.member.tier = 'BRONZE' THEN 1 ELSE 0 END)
		    )
		    FROM ClubStatistics cs
		    JOIN cs.club c
		    LEFT JOIN c.clubMembers cm
		    WHERE c.isClubDeleted = false
		    GROUP BY c.clubToken, c.clubName, c.clubDescription, c.clubImage, c.createdAt, c.modifiedAt, cs.popularityScore
		    ORDER BY cs.popularityScore DESC
		""")
	List<ClubCache> findTopPopularClubs(Pageable pageable);

	@Query("""
		    SELECT new org.badminton.domain.domain.club.vo.ClubCache(
		        c.clubToken, c.clubName, c.clubDescription, c.clubImage, c.createdAt, c.modifiedAt,
		        SUM(CASE WHEN cm.member.tier = 'GOLD' THEN 1 ELSE 0 END),
		        SUM(CASE WHEN cm.member.tier = 'SILVER' THEN 1 ELSE 0 END),
		        SUM(CASE WHEN cm.member.tier = 'BRONZE' THEN 1 ELSE 0 END)
		    )
		    FROM ClubStatistics cs
		    JOIN cs.club c
		    LEFT JOIN c.clubMembers cm
		    WHERE c.isClubDeleted = false
		    GROUP BY c.clubToken, c.clubName, c.clubDescription, c.clubImage, c.createdAt, c.modifiedAt, cs.activityScore
		    ORDER BY cs.activityScore DESC
		""")
	List<ClubCache> findTop10ByActivityClubs(Pageable pageable);

}
