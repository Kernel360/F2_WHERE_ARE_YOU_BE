package org.badminton.domain.domain.match.info;

import org.badminton.domain.domain.league.vo.Team;

public record TeamPlayerNameInfo(
        String participant1Name,
        String participant2Name
) {
    public static TeamPlayerNameInfo fromTeam(Team team, Long currentClubMemberId) {
        String participant1Name = team.getLeagueParticipant1().getClubMember().getMember().getName();
        String participant2Name = team.getLeagueParticipant2().getClubMember().getMember().getName();

        if (currentClubMemberId != null) {
            return getTeamResultResponse(team, currentClubMemberId, participant1Name, participant2Name);
        }
        return new TeamPlayerNameInfo(participant1Name, participant2Name);
    }

    private static TeamPlayerNameInfo getTeamResultResponse(Team team, Long currentClubMemberId,
                                                            String participant1Name, String participant2Name) {
        if (team.getLeagueParticipant1().getClubMember().getClubMemberId().equals(currentClubMemberId)) {
            return new TeamPlayerNameInfo(participant1Name, participant2Name);
        } else {
            return new TeamPlayerNameInfo(participant2Name, participant1Name);
        }
    }
}