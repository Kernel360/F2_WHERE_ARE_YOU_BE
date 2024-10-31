package org.badminton.domain.domain.match.info;

import org.badminton.domain.domain.league.vo.Team;
import org.badminton.domain.domain.match.entity.DoublesMatch;

public record DoublesMatchPlayerInfo(

        TeamInfo team1,
        TeamInfo team2
) {
    public static DoublesMatchPlayerInfo of(Team team1, Team team2) {
        return new DoublesMatchPlayerInfo(
                TeamInfo.fromTeam(team1),
                TeamInfo.fromTeam(team2)
        );
    }

    public static DoublesMatchPlayerInfo from(DoublesMatch doublesMatch) {
        return new DoublesMatchPlayerInfo(
                TeamInfo.fromTeam(doublesMatch.getTeam1()),
                TeamInfo.fromTeam(doublesMatch.getTeam2())
        );
    }
}
