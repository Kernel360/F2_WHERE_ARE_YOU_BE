package org.badminton.domain.infrastructures.match.repository;

import java.util.List;
import org.badminton.domain.domain.match.entity.DoublesMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DoublesMatchRepository extends JpaRepository<DoublesMatchEntity, Long> {

    List<DoublesMatchEntity> findAllByLeague_LeagueId(Long leagueId);

    @Query("SELECT doubleMatch FROM DoublesMatchEntity doubleMatch " +
            "WHERE (doubleMatch.team1.leagueParticipant1.clubMember.clubMemberId = :clubMemberId " +
            "OR doubleMatch.team1.leagueParticipant2.clubMember.clubMemberId = :clubMemberId " +
            "OR doubleMatch.team2.leagueParticipant1.clubMember.clubMemberId = :clubMemberId " +
            "OR doubleMatch.team2.leagueParticipant2.clubMember.clubMemberId = :clubMemberId) " +
            "AND doubleMatch.matchStatus = org.badminton.domain.common.enums.MatchStatus.COMPLETED")
    List<DoublesMatchEntity> findAllCompletedByClubMemberId(@Param("clubMemberId") Long clubMemberId);

    void deleteAllByLeague_LeagueId(Long leagueId);
}

