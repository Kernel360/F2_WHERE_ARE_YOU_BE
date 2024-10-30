package org.badminton.domain.domain.match.info;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.entity.SinglesSet;

public class SetInfo {

    @Getter
    @ToString
    @Builder
    public static class Main {
        private final Long matchId;
        private final int setIndex;
        private final int score1;
        private final int score2;
        private final MatchType matchType;
    }

    public static Main fromSinglesSet(Long matchId, int setIndex,
                                      SinglesSet singlesSet) {
        return Main.builder()
                .matchId(matchId)
                .setIndex(setIndex)
                .score1(singlesSet.getPlayer1Score())
                .score2(singlesSet.getPlayer2Score())
                .matchType(MatchType.SINGLES)
                .build();
    }

    public static Main fromDoublesSet(Long matchId, int setIndex,
                                      DoublesSet doublesSet) {
        return Main.builder()
                .matchId(matchId)
                .setIndex(setIndex)
                .score1(doublesSet.getTeam1Score())
                .score2(doublesSet.getTeam2Score())
                .matchType(MatchType.DOUBLES)
                .build();
    }


}
