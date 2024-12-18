package org.badminton.infrastructure.match.repository;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SinglesMatchRepository extends JpaRepository<SinglesMatch, Long> {

	@Query("SELECT COUNT(match) = 0 FROM SinglesMatch match " +
		"WHERE match.league.leagueId = :leagueId AND match.roundNumber = :roundNumber "
		+ "AND match.matchStatus NOT IN (:statuses)")
	boolean areAllMatchesFinishedOrBye(@Param("leagueId") Long leagueId, @Param("roundNumber") int roundNumber,
		@Param("statuses") List<MatchStatus> statuses);

	List<SinglesMatch> findAllByLeague_LeagueId(Long leagueId);

	@Query("""
		    SELECT sm
		    FROM SinglesMatch sm
		    JOIN FETCH sm.league l
		    JOIN FETCH l.club c
		    JOIN FETCH sm.leagueParticipant1 lp1
		    JOIN FETCH lp1.member m1
		    LEFT JOIN FETCH m1.leagueRecord lr1
		    JOIN FETCH lp1.clubMember cm1
		    JOIN FETCH sm.leagueParticipant2 lp2
		    JOIN FETCH lp2.member m2
		    LEFT JOIN FETCH m2.leagueRecord lr2
		    JOIN FETCH lp2.clubMember cm2
		    WHERE l.leagueId = :leagueId
		""")
	List<SinglesMatch> findAllByLeagueIdWithJpql(@Param("leagueId") Long leagueId);

	void deleteAllByLeague_LeagueId(Long leagueId);

	List<SinglesMatch> findAllByLeague_LeagueIdAndRoundNumber(Long leagueId, Integer roundNumber);

	@Query("SELECT MAX(singleMatch.roundNumber) FROM SinglesMatch singleMatch WHERE singleMatch.league.leagueId = :leagueId")
	Integer findTopRoundNumberByLeagueId(@Param("leagueId") Long leagueId);

	SinglesMatch findFirstByLeagueLeagueIdOrderByIdAsc(Long leagueId);

	@Query("SELECT COUNT(match) = 0 FROM SinglesMatch match WHERE match.league.leagueId = :leagueId AND match.matchStatus != 'FINISHED'")
	boolean allMatchesFinishedForLeague(@Param("leagueId") Long leagueId);

	@Query("SELECT NOT EXISTS (SELECT 1 FROM SinglesMatch match WHERE match.league.leagueId = :leagueId AND match.matchStatus NOT IN (:statuses))")
	boolean allMatchesNotStartedForLeague(@Param("leagueId") Long leagueId,
		@Param("statuses") List<MatchStatus> statuses);

	@Query("SELECT m FROM SinglesMatch m WHERE " +
		"(m.leagueParticipant1 = :leagueParticipant OR m.leagueParticipant2 = :leagueParticipant)")
	Optional<SinglesMatch> findByLeagueParticipant(
		@Param("leagueParticipant") LeagueParticipant leagueParticipant);

	Optional<SinglesMatch> findByIdAndLeagueLeagueId(Long matchId, Long leagueId);
}
