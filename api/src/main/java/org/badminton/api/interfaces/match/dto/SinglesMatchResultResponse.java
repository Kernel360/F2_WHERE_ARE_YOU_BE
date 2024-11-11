package org.badminton.api.interfaces.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.domain.match.info.SinglesMatchResultInfo;

public record SinglesMatchResultResponse(
        @Schema(description = "내 이름")
        String myName,
        @Schema(description = "상대 이름")
        String opponentName,
        @Schema(description = "내 매치 결과")
        MatchResult currentPlayerResult,
        @Schema(description = "상대 매치 결과")
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
