package org.badminton.api.interfaces.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.badminton.domain.domain.match.info.DoublesSetInfo;

public record DoublesSetResponse(
        @Schema(description = "세트 번호 (1 | 2 | 3)")
        int setNumber,
        @Schema(description = "스코어 1")
        int score1,
        @Schema(description = "스코어 2")
        int score2
) {

    public static List<DoublesSetResponse> fromDoublesSetInfoList(List<DoublesSetInfo> doublesSetInfoList) {
        List<DoublesSetResponse> doublesSetResponses = new ArrayList<>();
        for (DoublesSetInfo doublesSetInfo : doublesSetInfoList) {
            doublesSetResponses.add(new DoublesSetResponse(doublesSetInfo.setNumber(), doublesSetInfo.score1(),
                    doublesSetInfo.score2()));
        }
        return doublesSetResponses;
    }
}
