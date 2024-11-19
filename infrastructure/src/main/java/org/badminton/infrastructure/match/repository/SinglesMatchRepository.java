package org.badminton.infrastructure.match.repository;

import java.util.List;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SinglesMatchRepository extends JpaRepository<SinglesMatch, Long> {

    List<SinglesMatch> findAllByLeague_LeagueId(Long leagueId);

    void deleteAllByLeague_LeagueId(Long leagueId);

    List<SinglesMatch> findAllByLeague_LeagueIdAndRoundNumber(Long leagueId, Integer roundNumber);

    @Query("SELECT MAX(sm.roundNumber) FROM SinglesMatch sm WHERE sm.league.leagueId = :leagueId")
    Integer findTopRoundNumberByLeagueId(@Param("leagueId") Long leagueId);

    SinglesMatch findFirstByLeagueLeagueIdOrderByIdAsc(Long leagueId);

	@Query("SELECT COUNT(m) = 0 FROM SinglesMatch m WHERE m.league.leagueId = :leagueId AND m.matchStatus != 'FINISHED'")
	boolean allMatchesFinishedForLeague(@Param("leagueId") Long leagueId);

	@Query("SELECT COUNT(m) = 0 FROM SinglesMatch m WHERE m.league.leagueId = :leagueId AND m.matchStatus != 'NOT_STARTED'")
	boolean allMatchesNotStartedForLeague(@Param("leagueId") Long leagueId);

}
