package org.badminton.domain.domain.match.info;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.domain.match.entity.SinglesMatch;

public record SinglesMatchResultInfo(
	String currentPlayerName,
	String opponentName,
	MatchResult currentPlayerResult,
	MatchResult opponentResult
) {
	public static SinglesMatchResultInfo fromSinglesMatch(SinglesMatch singlesMatch, boolean isPlayer1,
		Long clubMemberId) {
		String player1Name = singlesMatch.getLeagueParticipant1().getClubMember().getMember().getName();
		String player2Name = singlesMatch.getLeagueParticipant2().getClubMember().getMember().getName();
		MatchResult player1Result = singlesMatch.getPlayer1MatchResult();
		MatchResult player2Result = singlesMatch.getPlayer2MatchResult();

		if (isPlayer1) {
			return new SinglesMatchResultInfo(player1Name, player2Name, player1Result, player2Result);
		} else {
			return new SinglesMatchResultInfo(player2Name, player1Name, player2Result, player1Result);
		}
	}
}