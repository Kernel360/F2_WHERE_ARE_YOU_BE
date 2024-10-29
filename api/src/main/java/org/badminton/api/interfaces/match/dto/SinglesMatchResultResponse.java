package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.domain.match.info.SinglesMatchResultInfo;

public record SinglesMatchResultResponse(
        String currentPlayerName,
        String opponentName,
        MatchResult currentPlayerResult,
        MatchResult opponentResult
) {
    public static SinglesMatchResultResponse from(SinglesMatchResultInfo singlesMatchResultInfo) {
        return new SinglesMatchResultResponse(
                singlesMatchResultInfo.currentPlayerName(),
                singlesMatchResultInfo.opponentName(),
                singlesMatchResultInfo.currentPlayerResult(),
                singlesMatchResultInfo.opponentResult()
        );
    }
}
