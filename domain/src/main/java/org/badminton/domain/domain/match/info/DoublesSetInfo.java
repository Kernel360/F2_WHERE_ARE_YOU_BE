package org.badminton.domain.domain.match.info;

import java.util.ArrayList;
import java.util.List;
import org.badminton.domain.domain.match.entity.DoublesSetEntity;

public record DoublesSetInfo(
        int setIndex,
        int score1,
        int score2
) {

    public static List<DoublesSetInfo> fromDoublesSets(List<DoublesSetEntity> doublesSets) {
        List<DoublesSetInfo> doublesSetResponseList = new ArrayList<>();
        for (DoublesSetEntity doublesSet : doublesSets) {
            doublesSetResponseList.add(new DoublesSetInfo(doublesSet.getSetIndex(), doublesSet.getTeam1Score(),
                    doublesSet.getTeam2Score()));
        }
        return doublesSetResponseList;
    }

}
