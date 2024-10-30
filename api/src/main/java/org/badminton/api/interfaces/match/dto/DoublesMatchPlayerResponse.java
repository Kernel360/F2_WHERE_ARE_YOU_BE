package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.DoublesMatchPlayerInfo;

public record DoublesMatchPlayerResponse(
        TeamResponse team1,
        TeamResponse team2
) {
    public static DoublesMatchPlayerResponse of(DoublesMatchPlayerInfo doublesMatchPlayerInfo) {
        if (doublesMatchPlayerInfo == null) {
            return null;
        }
        return new DoublesMatchPlayerResponse(
                TeamResponse.fromTeam(doublesMatchPlayerInfo.team1()),
                TeamResponse.fromTeam(doublesMatchPlayerInfo.team2())
        );
    }
}
