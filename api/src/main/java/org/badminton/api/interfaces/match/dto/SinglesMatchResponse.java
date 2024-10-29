package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.SinglesMatchInfo;

public record SinglesMatchResponse(
        Long matchId,
        String participant1Name,
        String participant1Image,
        int participant1WinSetCount,
        String participant2Name,
        String participant2Image,
        int participant2WinSetCount
) {

    public static SinglesMatchResponse fromSinglesMatchInfo(SinglesMatchInfo singlesMatchInfo) {
        return new SinglesMatchResponse(
                singlesMatchInfo.matchId(),
                singlesMatchInfo.participant1Name(), singlesMatchInfo.participant1Image(),
                singlesMatchInfo.participant1WinSetCount(), singlesMatchInfo.participant2Name(),
                singlesMatchInfo.participant2Image(),
                singlesMatchInfo.participant2WinSetCount());
    }
}
