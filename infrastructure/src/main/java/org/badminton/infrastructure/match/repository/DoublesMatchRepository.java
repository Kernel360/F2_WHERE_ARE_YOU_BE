package org.badminton.infrastructure.match.repository;

import java.util.List;

import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DoublesMatchRepository extends JpaRepository<DoublesMatch, Long> {

	List<DoublesMatch> findAllByLeague_LeagueId(Long leagueId);

	@Query("SELECT doubleMatch FROM DoublesMatch doubleMatch " +
		"WHERE (doubleMatch.team1.leagueParticipant1.clubMember.clubMemberId = :clubMemberId " +
		"OR doubleMatch.team1.leagueParticipant2.clubMember.clubMemberId = :clubMemberId " +
		"OR doubleMatch.team2.leagueParticipant1.clubMember.clubMemberId = :clubMemberId " +
		"OR doubleMatch.team2.leagueParticipant2.clubMember.clubMemberId = :clubMemberId) " +
		"AND doubleMatch.matchStatus = org.badminton.domain.common.enums.MatchStatus.COMPLETED")
	List<DoublesMatch> findAllCompletedByClubMemberId(@Param("clubMemberId") Long clubMemberId);

	void deleteAllByLeague_LeagueId(Long leagueId);
}

