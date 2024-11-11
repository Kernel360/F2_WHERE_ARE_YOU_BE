package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public record LeagueParticipationCancelResponse(
	@Schema(description = "경기 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
	Long leagueId,
	@Schema(description = "클럽 맴버 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
	Long clubMemberId,

	@Schema(description = "생성 일자", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime createdAt,

	@Schema(description = "취소 일자", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime deletedAt
) {

}
