package org.badminton.infrastructure.match.store;

import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.store.DoublesMatchStore;
import org.badminton.infrastructure.match.repository.DoublesMatchRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DoublesMatchStoreImpl implements DoublesMatchStore {

	private final DoublesMatchRepository doublesMatchRepository;

	@Override
	public void deleteDoublesBracket(Long leagueId) {
		doublesMatchRepository.deleteAllByLeague_LeagueId(leagueId);
	}

	@Override
	public void store(DoublesMatch doublesMatch) {
		doublesMatchRepository.save(doublesMatch);
	}
}
