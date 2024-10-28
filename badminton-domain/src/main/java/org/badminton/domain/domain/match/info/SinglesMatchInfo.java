package org.badminton.domain.domain.match.info;

import org.badminton.domain.domain.match.entity.SinglesMatch;

public record SinglesMatchInfo(
        Long matchId,
        String participant1Name,
        String participant1Image,
        int participant1WinSetCount,
        String participant2Name,
        String participant2Image,
        int participant2WinSetCount
) {

    public static SinglesMatchInfo fromSinglesMatch(SinglesMatch singlesMatch) {
        return new SinglesMatchInfo(
                singlesMatch.getId(),
                singlesMatch.getLeagueParticipant1().getMember().getName(),
                singlesMatch.getLeagueParticipant1().getMember().getProfileImage(),
                singlesMatch.getPlayer1WinSetCount(),
                singlesMatch.getLeagueParticipant2().getMember().getName(),
                singlesMatch.getLeagueParticipant2().getMember().getProfileImage(),
                singlesMatch.getPlayer2WinSetCount()
        );
    }
}
