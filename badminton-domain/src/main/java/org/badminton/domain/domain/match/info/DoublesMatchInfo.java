package org.badminton.domain.domain.match.info;

import org.badminton.domain.domain.match.entity.DoublesMatchEntity;

public record DoublesMatchInfo(
        Long matchId,
        TeamInfo team1,
        int team1WinSetCount,
        TeamInfo team2,
        int team2WinSetCount
) {

    public static DoublesMatchInfo fromDoublesMatchEntity(DoublesMatchEntity doublesMatch) {
        return new DoublesMatchInfo(
                doublesMatch.getDoublesMatchId(),
                TeamInfo.teamToTeamResponse(doublesMatch.getTeam1()),
                doublesMatch.getTeam1WinSetCount(),
                TeamInfo.teamToTeamResponse(doublesMatch.getTeam2()),
                doublesMatch.getTeam2WinSetCount()
        );
    }
}
