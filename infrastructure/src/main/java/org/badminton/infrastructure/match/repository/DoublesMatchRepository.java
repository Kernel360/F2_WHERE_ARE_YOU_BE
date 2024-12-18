package org.badminton.infrastructure.match.repository;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
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

	@Query("""
		    SELECT dm
		    FROM DoublesMatch dm
		    LEFT JOIN FETCH dm.league l
		    LEFT JOIN FETCH l.club c
		    LEFT JOIN FETCH dm.team1 t1
		    LEFT JOIN FETCH t1.leagueParticipant1 lp1
		    LEFT JOIN FETCH lp1.member m1
		    LEFT JOIN FETCH t1.leagueParticipant2 lp2
		    LEFT JOIN FETCH lp2.member m2
		    LEFT JOIN FETCH dm.team2 t2
		    LEFT JOIN FETCH t2.leagueParticipant1 lp3
		    LEFT JOIN FETCH lp3.member m3
		    LEFT JOIN FETCH t2.leagueParticipant2 lp4
		    LEFT JOIN FETCH lp4.member m4
		    WHERE dm.league.leagueId = :leagueId
		""")
	List<DoublesMatch> findAllByLeagueIdWithJpql(@Param("leagueId") Long leagueId);

	void deleteAllByLeague_LeagueId(Long leagueId);

	List<DoublesMatch> findAllByLeague_LeagueIdAndRoundNumber(Long leagueId, Integer roundNumber);

	DoublesMatch findFirstByLeagueLeagueIdOrderByIdAsc(Long leagueId);

	@Query("SELECT COUNT(m) = 0 FROM DoublesMatch m WHERE m.league.leagueId = :leagueId AND m.matchStatus != 'FINISHED'")
	boolean allMatchesFinishedForLeague(@Param("leagueId") Long leagueId);

	@Query("SELECT NOT EXISTS (SELECT 1 FROM DoublesMatch match WHERE match.league.leagueId = :leagueId AND match.matchStatus NOT IN (:statuses))")
	boolean allMatchesNotStartedForLeague(@Param("leagueId") Long leagueId,
		@Param("statuses") List<MatchStatus> statuses);

	@Query("SELECT m FROM DoublesMatch m WHERE " +
		"(m.team1.leagueParticipant1 = :leagueParticipant OR m.team1.leagueParticipant2 = :leagueParticipant) " +
		"OR (m.team2.leagueParticipant1 = :leagueParticipant OR m.team2.leagueParticipant2 = :leagueParticipant)")
	Optional<DoublesMatch> findByLeagueParticipant(
		@Param("leagueParticipant") LeagueParticipant leagueParticipant);

	Optional<DoublesMatch> findByIdAndLeagueLeagueId(Long matchId, Long leagueId);
}

