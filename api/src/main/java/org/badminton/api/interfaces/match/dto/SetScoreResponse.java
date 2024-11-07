package org.badminton.api.interfaces.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import org.badminton.domain.domain.match.info.SetInfo;

public record SetScoreResponse(
        @Schema(description = "매치 아이디", requiredMode = RequiredMode.REQUIRED)
        Long matchId,
        @Schema(description = "세트 번호 (1 | 2 | 3)", requiredMode = RequiredMode.REQUIRED)
        int setNumber,
        @Schema(description = "스코어 1", requiredMode = RequiredMode.REQUIRED)
        int score1,
        @Schema(description = "스코어 2", requiredMode = RequiredMode.REQUIRED)
        int score2
) {

    public static SetScoreResponse fromSetInfo(SetInfo.Main setInfo) {
        return new SetScoreResponse(setInfo.getMatchId(), setInfo.getSetNumber(), setInfo.getScore1(),
                setInfo.getScore2());
    }
}

