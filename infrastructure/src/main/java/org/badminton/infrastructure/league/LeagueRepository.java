package org.badminton.infrastructure.league;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeagueRepository extends JpaRepository<League, Long> {

	Optional<League> findByClubClubTokenAndLeagueId(String clubToken, Long leagueId);

	List<League> findAllByClubClubTokenAndLeagueAtBetween(String clubToken, LocalDateTime startOfMonth,
		LocalDateTime endOfMonth);

	Page<League> findAllByLeagueAtBetweenAndLeagueStatusNotIn(LocalDateTime startOfDay, LocalDateTime endOfDay,
		List<LeagueStatus> excludedLeagueStatusList, Pageable pageable);

	Integer countByClubClubIdAndLeagueStatus(Long clubId, LeagueStatus leagueStatus);

	Page<League> findAllByLeagueAtBetweenAndLeagueStatus(LocalDateTime startOfDay, LocalDateTime endOfDay,
		LeagueStatus leagueStatus, Pageable pageable);

	@Query("SELECT l.matchGenerationType FROM League l WHERE l.leagueId = :leagueId")
	Optional<MatchGenerationType> getMatchGenerationTypeByLeagueId(@Param("leagueId") Long leagueId);

	@Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM League l WHERE l.club.clubId = :clubId AND DATE(l.createdAt) = CURRENT_DATE")
	boolean existsByClubIdAndCreatedAtToday(@Param("clubId") Long clubId);

	List<League> findByLeagueStatus(LeagueStatus leagueStatus);
}
