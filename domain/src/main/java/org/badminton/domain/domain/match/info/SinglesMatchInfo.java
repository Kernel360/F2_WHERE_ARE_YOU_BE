package org.badminton.domain.domain.match.info;

import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.entity.SinglesMatch;

public record SinglesMatchInfo(
	Long matchId,
	String participant1Name,
	String participant1Image,
	int participant1WinSetCount,
	String participant2Name,
	String participant2Image,
	int participant2WinSetCount,
	int roundNumber
) {

	public static SinglesMatchInfo fromSinglesMatch(SinglesMatch singlesMatch) {
		return new SinglesMatchInfo(
			singlesMatch.getId(),
			getParticipantName(singlesMatch.getLeagueParticipant1()),
			getParticipantImage(singlesMatch.getLeagueParticipant1()),
			singlesMatch.getPlayer1WinSetCount(),
			getParticipantName(singlesMatch.getLeagueParticipant2()),
			getParticipantImage(singlesMatch.getLeagueParticipant2()),
			singlesMatch.getPlayer2WinSetCount(),
			singlesMatch.getRoundNumber()
		);
	}

	private static String getParticipantName(LeagueParticipant participant) {
		return (participant != null && participant.getMember() != null)
			? participant.getMember().getName()
			: null;
	}

	private static String getParticipantImage(LeagueParticipant participant) {
		return (participant != null && participant.getMember() != null)
			? participant.getMember().getProfileImage()
			: null;
	}
}
