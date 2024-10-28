package org.badminton.domain.infrastructures.match.repository;

import java.util.List;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SinglesMatchRepository extends JpaRepository<SinglesMatch, Long> {

    List<SinglesMatch> findAllByLeague_LeagueId(Long leagueId);

    @Query("SELECT singleMatch FROM SinglesMatch singleMatch " +
            "WHERE (singleMatch.leagueParticipant1.clubMember.clubMemberId = :clubMemberId " +
            "OR singleMatch.leagueParticipant2.clubMember.clubMemberId = :clubMemberId) " +
            "AND singleMatch.matchStatus = org.badminton.domain.common.enums.MatchStatus.COMPLETED")
    List<SinglesMatch> findAllCompletedByClubMemberId(@Param("clubMemberId") Long clubMemberId);


    void deleteAllByLeague_LeagueId(Long leagueId);

}
