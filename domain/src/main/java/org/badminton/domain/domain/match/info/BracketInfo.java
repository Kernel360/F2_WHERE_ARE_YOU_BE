package org.badminton.domain.domain.match.info;

import java.util.List;
import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;

public record BracketInfo(
        Long leagueId,
        MatchGenerationType matchGenerationType,
        MatchType matchType,
        LeagueStatus leagueStatus,
        int totalRound,
        List<SinglesMatchInfo> singlesMatchInfoList,
        List<DoublesMatchInfo> doublesMatchInfoList
) {

    public static BracketInfo fromSingles(int roundNumber, List<SinglesMatch> singlesMatchList) {
        SinglesMatch singlesMatch = singlesMatchList.get(0);
        return new BracketInfo(singlesMatch.getId(),
                singlesMatch.getLeague().getMatchGenerationType(),
                singlesMatch.getLeague().getMatchType(),
                singlesMatch.getLeague().getLeagueStatus(),
                roundNumber,
                singlesMatchList.stream()
                        .map(SinglesMatchInfo::fromSinglesMatch)
                        .toList()
                , null);
    }

    public static BracketInfo fromDoubles(int roundNumber, List<DoublesMatch> doublesMatchList) {
        DoublesMatch doublesMatch = doublesMatchList.get(0);
        return new BracketInfo(doublesMatch.getId(),
                doublesMatch.getLeague().getMatchGenerationType(),
                doublesMatch.getLeague().getMatchType(), doublesMatch.getLeague().getLeagueStatus(), roundNumber,
                null,
                doublesMatchList.stream()
                        .map(DoublesMatchInfo::fromDoublesMatch)
                        .toList());
    }
}
