package org.badminton.domain.domain.match.info;

import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.vo.Team;

public record TeamInfo(
	String participant1Name,
	String participant1Image,
	String participant2Name,
	String participant2Image
) {

	public static TeamInfo fromTeam(Team team) {
		if (team == null) {
			return null;
		}
		return new TeamInfo(
			getParticipantName(team.getLeagueParticipant1()),
			getParticipantImage(team.getLeagueParticipant1()),
			getParticipantName(team.getLeagueParticipant2()),
			getParticipantImage(team.getLeagueParticipant2())
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
