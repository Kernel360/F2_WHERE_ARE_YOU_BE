package org.badminton.domain.domain.match.info;

import org.badminton.domain.domain.league.vo.Team;

public record TeamInfo(
        String participant1Name,
        String participant1Image,
        String participant2Name,
        String participant2Image
) {

    public static TeamInfo fromTeam(Team team) {
        return new TeamInfo(
                team.getLeagueParticipant1().getMember().getName(),
                team.getLeagueParticipant1().getMember().getProfileImage(),
                team.getLeagueParticipant2().getMember().getName(),
                team.getLeagueParticipant2().getMember().getProfileImage()
        );
    }
}

