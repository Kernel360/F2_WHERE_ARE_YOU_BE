package org.badminton.domain.domain.match.reader;

import org.badminton.domain.domain.match.entity.SinglesMatchEntity;

public interface SinglesMatchStore {
    void deleteSinglesBracket(Long leagueId);

    void store(SinglesMatchEntity singlesMatch);
}
