package org.badminton.api.interfaces.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.badminton.domain.domain.match.info.SinglesMatchPlayerInfo;

public record SinglesMatchPlayerResponse(
        @Schema(description = "경기 참가자 1 이름")
        String participant1Name,
        @Schema(description = "경기 참가자 1 이미지")
        String participant1Image,
        @Schema(description = "경기 참가자 2 이름")
        String participant2Name,
        @Schema(description = "경기 참가자 2 이미지")
        String participant2Image
) {
    public static SinglesMatchPlayerResponse from(SinglesMatchPlayerInfo singlesMatchPlayerInfo) {

        if (singlesMatchPlayerInfo == null) {
            return null;
        }
        return new SinglesMatchPlayerResponse(singlesMatchPlayerInfo.participant1Name(),
                singlesMatchPlayerInfo.participant1Image(),
                singlesMatchPlayerInfo.participant2Name(),
                singlesMatchPlayerInfo.participant2Image());
    }
}
