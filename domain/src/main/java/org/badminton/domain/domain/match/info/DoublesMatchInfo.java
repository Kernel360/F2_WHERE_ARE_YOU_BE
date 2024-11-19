package org.badminton.domain.domain.match.info;

import java.util.ArrayList;
import java.util.List;
import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.domain.match.entity.DoublesMatch;

public record DoublesMatchInfo(
        Long matchId,
        MatchTeamInfo team1,
        int team1WinSetCount,
        MatchTeamInfo team2,
        int team2WinSetCount,
        int roundNumber,
        MatchStatus matchStatus,
        List<String> winnersToken
) {

    public static DoublesMatchInfo fromDoublesMatch(DoublesMatch doublesMatch) {
        return new DoublesMatchInfo(
                doublesMatch.getId(),
                MatchTeamInfo.fromTeam(doublesMatch.getTeam1(), doublesMatch.getTeam1WinSetCount()),
                doublesMatch.getTeam1WinSetCount(),
                MatchTeamInfo.fromTeam(doublesMatch.getTeam2(), doublesMatch.getTeam2WinSetCount()),
                doublesMatch.getTeam2WinSetCount(),
                doublesMatch.getRoundNumber(),
                doublesMatch.getMatchStatus(),
                getWinnersToken(doublesMatch)
        );
    }

    private static List<String> getWinnersToken(DoublesMatch doublesMatch) {
        if (doublesMatch.getTeam1MatchResult() == MatchResult.WIN) {
            String memberToken1 = doublesMatch.getTeam1().getLeagueParticipant1().getMember().getMemberToken();
            String memberToken2 = doublesMatch.getTeam1().getLeagueParticipant2().getMember().getMemberToken();
            List<String> winnersToken = new ArrayList<>();
            winnersToken.add(memberToken1);
            winnersToken.add(memberToken2);
            return winnersToken;
        } else if (doublesMatch.getTeam2MatchResult() == MatchResult.WIN) {
            String memberToken1 = doublesMatch.getTeam2().getLeagueParticipant1().getMember().getMemberToken();
            String memberToken2 = doublesMatch.getTeam2().getLeagueParticipant2().getMember().getMemberToken();
            List<String> winnersToken = new ArrayList<>();
            winnersToken.add(memberToken1);
            winnersToken.add(memberToken2);
            return winnersToken;
        }
        return null;
    }
}
