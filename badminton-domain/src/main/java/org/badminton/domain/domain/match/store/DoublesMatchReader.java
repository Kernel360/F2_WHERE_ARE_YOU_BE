package org.badminton.domain.domain.match.store;

import java.util.List;
import org.badminton.domain.domain.match.entity.DoublesMatchEntity;

public interface DoublesMatchReader {
    List<DoublesMatchEntity> getDoublesBracket(Long leagueId);

    boolean checkIfBracketEmpty(Long leagueId);

    DoublesMatchEntity getDoublesMatch(Long matchId);
}
