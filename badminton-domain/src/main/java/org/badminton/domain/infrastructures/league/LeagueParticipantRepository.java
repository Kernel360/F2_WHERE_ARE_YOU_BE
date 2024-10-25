package org.badminton.domain.infrastructures.league;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.domain.league.entity.LeagueParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueParticipantRepository extends JpaRepository<LeagueParticipantEntity, Long> {

    Optional<LeagueParticipantEntity> findByLeagueLeagueIdAndClubMemberClubMemberIdAndCanceledFalse(Long leagueId,
                                                                                                    Long clubMemberId);

    List<LeagueParticipantEntity> findByMemberMemberTokenAndLeagueLeagueIdAndCanceledFalse(String memberToken, Long leagueId);

    List<LeagueParticipantEntity> findAllByLeagueLeagueIdAndCanceledFalse(Long leagueId);

    int countByLeagueLeagueIdAndCanceledFalse(Long leagueId);
}
