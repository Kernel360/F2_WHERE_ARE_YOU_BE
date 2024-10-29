package org.badminton.domain.domain.match.info;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.entity.DoublesSetEntity;
import org.badminton.domain.domain.match.entity.SinglesSetEntity;

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
                                      SinglesSetEntity singlesSetEntity) {
        return Main.builder()
                .matchId(matchId)
                .setIndex(setIndex)
                .score1(singlesSetEntity.getPlayer1Score())
                .score2(singlesSetEntity.getPlayer2Score())
                .matchType(MatchType.SINGLES)
                .build();
    }

    public static Main fromDoublesSet(Long matchId, int setIndex,
                                      DoublesSetEntity doublesSetEntity) {
        return Main.builder()
                .matchId(matchId)
                .setIndex(setIndex)
                .score1(doublesSetEntity.getTeam1Score())
                .score2(doublesSetEntity.getTeam2Score())
                .matchType(MatchType.DOUBLES)
                .build();
    }


}
