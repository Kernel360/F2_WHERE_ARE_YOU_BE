package org.badminton.infrastructure.club;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.vo.ClubCache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClubRepository extends JpaRepository<Club, Long> {
	Optional<Club> findByClubToken(String clubToken);

	Optional<Club> findByClubTokenAndIsClubDeletedFalse(String clubToken);

	Page<Club> findAllByClubNameContainingIgnoreCaseAndIsClubDeletedIsFalse(String keyword, Pageable pageable);
	
	List<Club> findTop10ByIsClubDeletedIsFalseOrderByCreatedAtDesc();

	Club findByClubId(Long clubId);

	boolean existsByClubNameAndIsClubDeletedFalse(String clubName);

	@Query(value = """
		    SELECT new org.badminton.domain.domain.club.vo.ClubCache(
		        c.clubToken, c.clubName, c.clubDescription, c.clubImage, c.createdAt, c.modifiedAt,
		        SUM(CASE WHEN cm.member.tier = 'GOLD' THEN 1 ELSE 0 END),
		        SUM(CASE WHEN cm.member.tier = 'SILVER' THEN 1 ELSE 0 END),
		        SUM(CASE WHEN cm.member.tier = 'BRONZE' THEN 1 ELSE 0 END)
		    )
		    FROM Club c
		    LEFT JOIN c.clubMembers cm
		    JOIN cm.member m
		    WHERE c.isClubDeleted = false
		    GROUP BY c.clubToken, c.clubName, c.clubDescription, c.clubImage, c.createdAt, c.modifiedAt, c.clubId
		""")
	Page<ClubCache> findAllClubs(Pageable pageable);
}

