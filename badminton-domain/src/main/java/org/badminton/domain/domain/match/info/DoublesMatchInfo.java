package org.badminton.domain.domain.match.info;

import org.badminton.domain.domain.match.entity.DoublesMatch;

public record DoublesMatchInfo(
        Long matchId,
        TeamInfo team1,
        int team1WinSetCount,
        TeamInfo team2,
        int team2WinSetCount
) {

    public static DoublesMatchInfo fromDoublesMatch(DoublesMatch doublesMatch) {
        return new DoublesMatchInfo(
                doublesMatch.getId(),
                TeamInfo.teamToTeamResponse(doublesMatch.getTeam1()),
                doublesMatch.getTeam1WinSetCount(),
                TeamInfo.teamToTeamResponse(doublesMatch.getTeam2()),
                doublesMatch.getTeam2WinSetCount()
        );
    }
}
