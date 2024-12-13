package org.badminton.domain.domain.match.store;

import org.badminton.domain.domain.match.entity.SinglesMatch;

public interface SinglesMatchTournamentStore {
	void updateSinglesMatchNextRoundMatch(SinglesMatch singlesMatch);
}
