package org.badminton.infrastructure.match.store;

import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.store.SinglesMatchStore;
import org.badminton.infrastructure.match.repository.SinglesMatchRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SinglesMatchStoreImpl implements SinglesMatchStore {
	private final SinglesMatchRepository singlesMatchRepository;

	@Override
	public void deleteSinglesBracket(Long leagueId) {
		singlesMatchRepository.deleteAllByLeague_LeagueId(leagueId);
	}

	@Override
	public void store(SinglesMatch singlesMatch) {
		singlesMatchRepository.save(singlesMatch);
	}
}
