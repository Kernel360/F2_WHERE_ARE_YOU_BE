package org.badminton.api.interfaces.match.dto;

import java.util.ArrayList;
import java.util.List;
import org.badminton.domain.domain.match.info.DoublesSetInfo;

public record DoublesSetResponse(
        int setIndex,
        int score1,
        int score2
) {

    public static List<DoublesSetResponse> fromDoublesSetInfoList(List<DoublesSetInfo> doublesSetInfoList) {
        List<DoublesSetResponse> doublesSetResponses = new ArrayList<>();
        for (DoublesSetInfo doublesSetInfo : doublesSetInfoList) {
            doublesSetResponses.add(new DoublesSetResponse(doublesSetInfo.setIndex(), doublesSetInfo.score1(),
                    doublesSetInfo.score2()));
        }
        return doublesSetResponses;
    }
}
