package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.MatchTeamInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record MatchTeamResponse(
	@Schema(description = "참가자1", requiredMode = Schema.RequiredMode.REQUIRED)
	Participant participant1,
	@Schema(description = "참가자2", requiredMode = Schema.RequiredMode.REQUIRED)
	Participant participant2,
	@Schema(description = "이긴 세트 수", requiredMode = Schema.RequiredMode.REQUIRED)
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

