package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.DoublesMatchInfo;

public record DoublesMatchResponse(
        Long matchId,
        TeamResponse team1,
        int team1WinSetCount,
        TeamResponse team2,
        int team2WinSetCount
) {

    public static DoublesMatchResponse fromDoublesMatchInfo(DoublesMatchInfo doublesMatchInfo) {
        return new DoublesMatchResponse(
                doublesMatchInfo.matchId(),
                TeamResponse.fromTeam(doublesMatchInfo.team1()),
                doublesMatchInfo.team1WinSetCount(),
                TeamResponse.fromTeam(doublesMatchInfo.team2()),
                doublesMatchInfo.team2WinSetCount()
        );
    }
}
