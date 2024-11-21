package org.badminton.api.interfaces.league.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record IsLeagueParticipantResponse(
	@Schema(description = "해당하는 경기에 참여 신청을 했는지 여부", requiredMode = Schema.RequiredMode.REQUIRED)
	boolean isParticipatedInLeague
) {
}
