package org.badminton.domain.domain.match.store;

import java.util.List;
import org.badminton.domain.domain.match.entity.SinglesMatch;

public interface SinglesMatchReader {
    List<SinglesMatch> getSinglesBracket(Long leagueId);

    boolean checkIfBracketEmpty(Long leagueId);

    SinglesMatch getSinglesMatch(Long matchId);
}
