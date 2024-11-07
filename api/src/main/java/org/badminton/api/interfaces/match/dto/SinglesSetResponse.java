package org.badminton.api.interfaces.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.badminton.domain.domain.match.info.SinglesSetInfo;

public record SinglesSetResponse(
        @Schema(description = "세트 번호 (1 | 2 | 3)")
        int setNumber,
        @Schema(description = "스코어 1")
        int score1,
        @Schema(description = "스코어 2")
        int score2
) {

    public static List<SinglesSetResponse> fromSinglesSetInfoList(List<SinglesSetInfo> singlesSetInfoList) {
        List<SinglesSetResponse> singlesSetResponses = new ArrayList<>();
        for (SinglesSetInfo singlesSetInfo : singlesSetInfoList) {
            singlesSetResponses.add(new SinglesSetResponse(singlesSetInfo.setNumber(), singlesSetInfo.score1(),
                    singlesSetInfo.score2()));
        }
        return singlesSetResponses;
    }
}
