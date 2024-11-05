package org.badminton.infrastructure.match.repository;

import java.util.List;

import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SinglesMatchRepository extends JpaRepository<SinglesMatch, Long> {

	List<SinglesMatch> findAllByLeague_LeagueId(Long leagueId);

	void deleteAllByLeague_LeagueId(Long leagueId);

}
