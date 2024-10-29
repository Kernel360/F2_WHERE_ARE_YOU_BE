package org.badminton.infrastructure.league;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueParticipantRepository extends JpaRepository<LeagueParticipant, Long> {

	Optional<LeagueParticipant> findByLeagueLeagueIdAndClubMemberClubMemberIdAndCanceledFalse(Long leagueId,
		Long clubMemberId);

	List<LeagueParticipant> findByMemberMemberTokenAndLeagueLeagueIdAndCanceledFalse(String memberToken, Long leagueId);

	List<LeagueParticipant> findAllByLeagueLeagueIdAndCanceledFalse(Long leagueId);

	int countByLeagueLeagueIdAndCanceledFalse(Long leagueId);
}
