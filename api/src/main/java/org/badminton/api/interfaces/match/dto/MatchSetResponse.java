package org.badminton.api.interfaces.match.dto;


import org.badminton.domain.domain.match.info.MatchSetInfo;

public record MatchSetResponse(
        SinglesMatchPlayerResponse singlesMatchPlayerResponse,
        DoublesMatchPlayerResponse doublesMatchPlayerResponse,
        int setScore1,
        int setScore2,
        int winSetScore1,
        int winSetScore2,
        int setNumber
) {

    public static MatchSetResponse from(MatchSetInfo matchSetInfo) {
        return new MatchSetResponse(
                SinglesMatchPlayerResponse.from(matchSetInfo.singlesMatchPlayerInfo()),
                DoublesMatchPlayerResponse.of(matchSetInfo.doublesMatchPlayerInfo()),
                matchSetInfo.setScore1(),
                matchSetInfo.setScore2(),
                matchSetInfo.winSetScore1(),
                matchSetInfo.winSetScore2(),
                matchSetInfo.setNumber()
        );
    }

}
