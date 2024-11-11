package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.TeamInfo;

public record TeamResponse(
	String participant1Name,
	String participant1Image,
	String participant2Name,
	String participant2Image
) {

	public static TeamResponse fromTeam(TeamInfo teamInfo) {
		if (teamInfo == null) {
			return new TeamResponse(null, null, null, null);
		}
		return new TeamResponse(
			teamInfo.participant1Name(),
			teamInfo.participant1Image(),
			teamInfo.participant2Name(),
			teamInfo.participant2Image()
		);
	}
}

