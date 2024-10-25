package org.badminton.domain.domain.match.store;

import java.util.List;
import org.badminton.domain.domain.match.entity.SinglesMatchEntity;

public interface SinglesMatchReader {
    List<SinglesMatchEntity> getSinglesBracket(Long leagueId);

    boolean checkIfBracketEmpty(Long leagueId);

    SinglesMatchEntity getSinglesMatch(Long matchId);
}
