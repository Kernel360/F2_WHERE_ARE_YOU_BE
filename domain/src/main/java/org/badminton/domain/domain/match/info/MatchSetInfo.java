package org.badminton.domain.domain.match.info;

import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.entity.SinglesSet;

public record MatchSetInfo(
        SinglesMatchPlayerInfo singlesMatchPlayerInfo,
        DoublesMatchPlayerInfo doublesMatchPlayerInfo,
        int setScore1,
        int setScore2,
        int winSetScore1,
        int winSetScore2,
        int setNumber
) {

    public static MatchSetInfo fromSingles(SinglesSet singlesSet) {
        return new MatchSetInfo(
                SinglesMatchPlayerInfo.fromSinglesMatch(singlesSet.getSinglesMatch()),
                null,
                singlesSet.getPlayer1Score(),
                singlesSet.getPlayer2Score(),
                singlesSet.getSinglesMatch().getPlayer1WinSetCount(),
                singlesSet.getSinglesMatch().getPlayer2WinSetCount(),
                singlesSet.getSetNumber()
        );
    }

    public static MatchSetInfo fromDoubles(DoublesSet doublesSet) {
        return new MatchSetInfo(
                null,
                DoublesMatchPlayerInfo.from(doublesSet.getDoublesMatch()),
                doublesSet.getTeam1Score(),
                doublesSet.getTeam2Score(),
                doublesSet.getDoublesMatch().getTeam1WinSetCount(),
                doublesSet.getDoublesMatch().getTeam2WinSetCount(),
                doublesSet.getSetNumber()
        );
    }

}
