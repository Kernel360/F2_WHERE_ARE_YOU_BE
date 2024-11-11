package org.badminton.infrastructure.match.repository;

import java.util.List;

import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoublesMatchRepository extends JpaRepository<DoublesMatch, Long> {

	List<DoublesMatch> findAllByLeague_LeagueId(Long leagueId);

	void deleteAllByLeague_LeagueId(Long leagueId);

	List<DoublesMatch> findAllByLeague_LeagueIdAndRoundNumber(Long leagueId, Integer roundNumber);

	DoublesMatch findFirstByLeagueLeagueIdOrderByIdAsc(Long leagueId);

}

