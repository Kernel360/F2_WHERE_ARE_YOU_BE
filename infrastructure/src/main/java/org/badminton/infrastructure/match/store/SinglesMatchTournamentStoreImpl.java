package org.badminton.infrastructure.match.store;

import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.store.SinglesMatchTournamentStore;
import org.badminton.infrastructure.match.strategy.MatchUtils;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SinglesMatchTournamentStoreImpl implements SinglesMatchTournamentStore {

	private final MatchUtils matchUtils;

	@Override
	public void updateSinglesMatchNextRoundMatch(SinglesMatch singlesMatch) {
		matchUtils.updateSinglesMatchNextRoundMatch(singlesMatch);
	}
}
