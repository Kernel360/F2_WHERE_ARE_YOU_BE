package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;

import io.swagger.v3.oas.annotations.media.Schema;

public record LeagueReadResponse(
	@Schema(description = "경기 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
	Long leagueId,

	@Schema(description = "경기 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String leagueName,

	@Schema(description = "현재 경기 상태( ALL | RECRUITING | RECRUITING_COMPLETED | PLAYING | CANCELED | FINISHED)",
		requiredMode = Schema.RequiredMode.REQUIRED)
	LeagueStatus status,

	@Schema(description = "경기 시작 날짜", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime leagueAt,

	@Schema(description = "참가 인원", requiredMode = Schema.RequiredMode.REQUIRED)
	int playerCount
) {
	public LeagueReadResponse(League entity) {
		this(
			entity.getLeagueId(),
			entity.getLeagueName(),
			entity.getLeagueStatus(),
			entity.getLeagueAt(),
			entity.getPlayerLimitCount()
		);
	}

	public static LeagueReadResponse leagueReadEntityToResponse(League entity) {
		return new LeagueReadResponse(entity);
	}
}
