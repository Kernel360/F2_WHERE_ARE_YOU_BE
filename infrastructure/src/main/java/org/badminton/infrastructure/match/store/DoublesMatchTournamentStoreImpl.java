package org.badminton.infrastructure.match.store;

import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.store.DoublesMatchTournamentStore;
import org.badminton.infrastructure.match.strategy.MatchUtils;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DoublesMatchTournamentStoreImpl implements DoublesMatchTournamentStore {
	private final MatchUtils matchUtils;

	@Override
	public void updateDoublesMatchNextRoundMatch(DoublesMatch doublesMatch) {
		matchUtils.updateDoublesMatchNextRoundMatch(doublesMatch);
	}
}
