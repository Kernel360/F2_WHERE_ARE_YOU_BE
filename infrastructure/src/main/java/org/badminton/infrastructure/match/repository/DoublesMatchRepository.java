package org.badminton.infrastructure.match.repository;

import java.util.List;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DoublesMatchRepository extends JpaRepository<DoublesMatch, Long> {

    List<DoublesMatch> findAllByLeague_LeagueId(Long leagueId);

    void deleteAllByLeague_LeagueId(Long leagueId);

    List<DoublesMatch> findAllByLeague_LeagueIdAndRoundNumber(Long leagueId, Integer roundNumber);

    DoublesMatch findFirstByLeagueLeagueIdOrderByIdAsc(Long leagueId);

	@Query("SELECT COUNT(m) = 0 FROM DoublesMatch m WHERE m.league.leagueId = :leagueId AND m.matchStatus != 'FINISHED'")
	boolean allMatchesFinishedForLeague(@Param("leagueId") Long leagueId);

	@Query("SELECT COUNT(m) = 0 FROM DoublesMatch m WHERE m.league.leagueId = :leagueId AND m.matchStatus != 'NOT_STARTED'")
	boolean allMatchesNotStartedForLeague(@Param("leagueId") Long leagueId);

}

