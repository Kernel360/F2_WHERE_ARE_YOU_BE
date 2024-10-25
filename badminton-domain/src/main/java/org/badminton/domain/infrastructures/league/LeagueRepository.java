package org.badminton.domain.infrastructures.league;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.badminton.domain.domain.league.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {

	Optional<League> findByClubClubTokenAndLeagueId(String clubToken, Long leagueId);

	List<League> findAllByClubClubIdAndLeagueAtBetween(Long clubId, LocalDateTime startOfMonth,
		LocalDateTime endOfMonth);

	void deleteByLeagueId(Long leagueId);
}
