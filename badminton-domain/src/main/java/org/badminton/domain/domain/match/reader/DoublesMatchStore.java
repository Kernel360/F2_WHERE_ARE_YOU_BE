package org.badminton.domain.domain.match.reader;

import org.badminton.domain.domain.match.entity.DoublesMatchEntity;

public interface DoublesMatchStore {
    void deleteDoublesBracket(Long leagueId);

    void store(DoublesMatchEntity doublesMatch);
}
