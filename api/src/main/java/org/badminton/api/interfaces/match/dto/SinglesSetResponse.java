package org.badminton.api.interfaces.match.dto;

import java.util.ArrayList;
import java.util.List;
import org.badminton.domain.domain.match.info.SinglesSetInfo;

public record SinglesSetResponse(

        int setIndex,
        int score1,
        int score2
) {

    public static List<SinglesSetResponse> fromSinglesSetInfoList(List<SinglesSetInfo> singlesSetInfoList) {
        List<SinglesSetResponse> singlesSetResponses = new ArrayList<>();
        for (SinglesSetInfo singlesSetInfo : singlesSetInfoList) {
            singlesSetResponses.add(new SinglesSetResponse(singlesSetInfo.setIndex(), singlesSetInfo.score1(),
                    singlesSetInfo.score2()));
        }
        return singlesSetResponses;
    }
}
