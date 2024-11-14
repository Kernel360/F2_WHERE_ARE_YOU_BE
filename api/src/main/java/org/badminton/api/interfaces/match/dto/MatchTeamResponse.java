package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.MatchTeamInfo;

public record MatchTeamResponse(
	Participant participant1,
	Participant participant2,
	int team1WinSetCount
) {

	public static MatchTeamResponse fromTeam(MatchTeamInfo teamInfo) {
		if (teamInfo == null) {
			return new MatchTeamResponse(null, null, 0);
		}
		return new MatchTeamResponse(
			new Participant(teamInfo.participant1MemberToken(), teamInfo.participant1Name(),
				teamInfo.participant1Image(), teamInfo.participant1Tier(), teamInfo.winSetCounts()),
			new Participant(teamInfo.participant2MemberToken(), teamInfo.participant2Name(),
				teamInfo.participant2Image(), teamInfo.participant2Tier(), teamInfo.winSetCounts()),
			teamInfo.winSetCounts()
		);
	}
}

