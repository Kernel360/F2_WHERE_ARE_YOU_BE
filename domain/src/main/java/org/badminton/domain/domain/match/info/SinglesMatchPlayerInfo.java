package org.badminton.domain.domain.match.info;

import org.badminton.domain.domain.match.entity.SinglesMatch;

public record SinglesMatchPlayerInfo(
	String participant1Name,
	String participant1Image,
	String participant2Name,
	String participant2Image
) {
	public static SinglesMatchPlayerInfo fromSinglesMatch(SinglesMatch singlesMatch) {
		return new SinglesMatchPlayerInfo(
			singlesMatch.getLeagueParticipant1().getMember().getName(),
			singlesMatch.getLeagueParticipant1().getMember().getProfileImage(),
			singlesMatch.getLeagueParticipant2().getMember().getName(),
			singlesMatch.getLeagueParticipant2().getMember().getProfileImage()
		);
	}
}
