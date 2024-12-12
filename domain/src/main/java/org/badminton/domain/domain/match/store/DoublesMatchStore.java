package org.badminton.domain.domain.match.store;

import org.badminton.domain.domain.match.entity.DoublesMatch;

public interface DoublesMatchStore {
	void deleteDoublesBracket(Long leagueId);

	void store(DoublesMatch doublesMatch);
}
