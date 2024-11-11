package org.badminton.api.interfaces.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.badminton.domain.domain.match.info.SinglesMatchInfo;

public record SinglesMatchResponse(
        @Schema(description = "매치 아이디")
        Long matchId,
        @Schema(description = "경기 참가자 1 이름")
        String participant1Name,
        @Schema(description = "경기 참가자 1 이미지")
        String participant1Image,
        @Schema(description = "경기 참가자 1 이긴 세트수")
        int participant1WinSetCount,
        @Schema(description = "경기 참가자 2 이름")
        String participant2Name,
        @Schema(description = "경기 참가자 2 이미지")
        String participant2Image,
        @Schema(description = "경기 참가자 2 이긴 세트수")
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
