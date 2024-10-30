package org.badminton.domain.domain.match.info;

import java.util.List;
import org.badminton.domain.common.enums.BracketType;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;

public record BracketInfo(
        Long leagueId,
        BracketType bracketType,
        MatchType matchType,
        MatchStatus matchStatus,
        int roundNumber,
        List<SinglesMatchInfo> singlesMatchInfoList,
        List<DoublesMatchInfo> doublesMatchInfoList
) {

    public static BracketInfo fromSingles(int roundNumber, List<SinglesMatch> singlesMatchList) {
        SinglesMatch singlesMatch = singlesMatchList.get(0);
        return new BracketInfo(singlesMatch.getId(),
                BracketType.FREE,
                singlesMatch.getLeague().getMatchType(),
                singlesMatch.getMatchStatus(),
                roundNumber,
                singlesMatchList.stream()
                        .map(SinglesMatchInfo::fromSinglesMatch)
                        .toList()
                , null);
    }

    public static BracketInfo fromDoubles(int roundNumber, List<DoublesMatch> doublesMatchList) {
        DoublesMatch doublesMatch = doublesMatchList.get(0);
        return new BracketInfo(doublesMatch.getId(), BracketType.FREE, doublesMatch.getLeague()
                .getMatchType(), doublesMatch.getMatchStatus(), roundNumber,
                null,
                doublesMatchList.stream()
                        .map(DoublesMatchInfo::fromDoublesMatch)
                        .toList());
    }
}