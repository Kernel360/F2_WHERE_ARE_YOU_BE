package org.badminton.api.interfaces.league.dto;

import org.badminton.domain.domain.league.enums.LeagueStatus;

import io.swagger.v3.oas.annotations.media.Schema;

public record LeagueRecruitingCompleteResponse(
	@Schema(description = "경기 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
	Long leagueId,
	@Schema(description = "현재 경기 상태( ALL | RECRUITING | RECRUITING_COMPLETED | PLAYING | CANCELED | FINISHED)",
		requiredMode = Schema.RequiredMode.REQUIRED)
	LeagueStatus leagueStatus
) {
}
