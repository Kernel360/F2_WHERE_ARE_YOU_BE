package org.badminton.domain.domain.match.reader;

import org.badminton.domain.domain.match.entity.SinglesMatch;

public interface SinglesMatchStore {
	void deleteSinglesBracket(Long leagueId);

	void store(SinglesMatch singlesMatch);
}
