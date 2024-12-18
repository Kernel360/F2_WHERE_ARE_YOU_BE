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
		    JOIN FETCH dm.league l
		    JOIN FETCH l.club c
		    JOIN FETCH dm.team1 t1
		    JOIN FETCH t1.leagueParticipant1 lp1
		    JOIN FETCH lp1.member m1
		    LEFT JOIN FETCH m1.leagueRecord lr1
		    JOIN FETCH lp1.clubMember cm1
		    JOIN FETCH t1.leagueParticipant2 lp2
		    JOIN FETCH lp2.member m2
		    LEFT JOIN FETCH m2.leagueRecord lr2
		    JOIN FETCH lp2.clubMember cm2
		    JOIN FETCH dm.team2 t2
		    JOIN FETCH t2.leagueParticipant1 lp3
		    JOIN FETCH lp3.member m3
		    LEFT JOIN FETCH m3.leagueRecord lr3
		    JOIN FETCH lp3.clubMember cm3
		    JOIN FETCH t2.leagueParticipant2 lp4
		    JOIN FETCH lp4.member m4
		    LEFT JOIN FETCH m4.leagueRecord lr4
		    JOIN FETCH lp4.clubMember cm4
		    WHERE dm.league.leagueId = :leagueId
		""")
	List<DoublesMatch> findAllByLeagueIdWithAllByJpql(@Param("leagueId") Long leagueId);

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

