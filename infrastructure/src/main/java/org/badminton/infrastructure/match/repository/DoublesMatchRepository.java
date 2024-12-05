package org.badminton.infrastructure.match.repository;

import java.util.List;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DoublesMatchRepository extends JpaRepository<DoublesMatch, Long> {

	@Query("SELECT COUNT(match) = 0 FROM DoublesMatch match " +
		"WHERE match.league.leagueId = :leagueId AND match.roundNumber = :roundNumber "
		+ "AND match.matchStatus NOT IN (:statuses)")
	boolean areAllMatchesFinishedOrBye(@Param("leagueId") Long leagueId, @Param("roundNumber") int roundNumber,
		@Param("statuses") List<MatchStatus> statuses);

	List<DoublesMatch> findAllByLeague_LeagueId(Long leagueId);

	void deleteAllByLeague_LeagueId(Long leagueId);

	List<DoublesMatch> findAllByLeague_LeagueIdAndRoundNumber(Long leagueId, Integer roundNumber);

	DoublesMatch findFirstByLeagueLeagueIdOrderByIdAsc(Long leagueId);

	@Query("SELECT COUNT(m) = 0 FROM DoublesMatch m WHERE m.league.leagueId = :leagueId AND m.matchStatus != 'FINISHED'")
	boolean allMatchesFinishedForLeague(@Param("leagueId") Long leagueId);

	@Query("SELECT NOT EXISTS (SELECT 1 FROM DoublesMatch match WHERE match.league.leagueId = :leagueId AND match.matchStatus NOT IN (:statuses))")
	boolean allMatchesNotStartedForLeague(@Param("leagueId") Long leagueId,
		@Param("statuses") List<MatchStatus> statuses);

}

