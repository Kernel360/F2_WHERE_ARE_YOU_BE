package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.domain.match.entity.SinglesMatch;

public record SinglesMatchResultResponse(
        String currentPlayerName,
        String opponentName,
        MatchResult currentPlayerResult,
        MatchResult opponentResult
) {
    public static SinglesMatchResultResponse fromSinglesMatch(SinglesMatch singlesMatch, boolean isPlayer1,
                                                              Long clubMemberId) {
        String player1Name = singlesMatch.getLeagueParticipant1().getClubMember().getMember().getName();
        String player2Name = singlesMatch.getLeagueParticipant2().getClubMember().getMember().getName();
        MatchResult player1Result = singlesMatch.getPlayer1MatchResult();
        MatchResult player2Result = singlesMatch.getPlayer2MatchResult();

        if (isPlayer1) {
            return new SinglesMatchResultResponse(player1Name, player2Name, player1Result, player2Result);
        } else {
            return new SinglesMatchResultResponse(player2Name, player1Name, player2Result, player1Result);
        }
    }
}
