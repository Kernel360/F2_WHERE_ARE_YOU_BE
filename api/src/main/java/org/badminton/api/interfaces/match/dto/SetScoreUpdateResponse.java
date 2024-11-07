package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.info.SetInfo;

public record SetScoreUpdateResponse(
        Long matchId,
        int setIndex,
        int score1,
        int score2,
        MatchType matchType
) {

    public static SetScoreUpdateResponse fromUpdateSetScoreInfo(SetInfo.Main setInfo) {
        return new SetScoreUpdateResponse(setInfo.getMatchId(), setInfo.getSetNumber(), setInfo.getScore1(),
                setInfo.getScore2(), setInfo.getMatchType());
    }

}
