package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.SetInfo;

public record SetScoreResponse(
        Long matchId,
        int setIndex,
        int score1,
        int score2
) {

    public static SetScoreResponse fromSetInfo(SetInfo.Main setInfo) {
        return new SetScoreResponse(setInfo.getMatchId(), setInfo.getSetIndex(), setInfo.getScore1(),
                setInfo.getScore2());
    }
}

