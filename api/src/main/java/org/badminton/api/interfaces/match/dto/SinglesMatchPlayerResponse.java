package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.SinglesMatchPlayerInfo;

public record SinglesMatchPlayerResponse(
        String participant1Name,
        String participant1Image,
        String participant2Name,
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
