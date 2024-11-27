package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.info.LeagueParticipantInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record LeagueParticipantResponse(
	@Schema(description = "경기 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
	Long leagueId,
	@Schema(description = "경기 참여자의 유저 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
	String memberToken,
	@Schema(description = "경기 생성 날짜", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime createdAt,
	@Schema(description = "경기 수정 날짜", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime modifiedAt
) {

	public static LeagueParticipantResponse from(LeagueParticipantInfo leagueParticipant) {
		return new LeagueParticipantResponse(leagueParticipant.leagueId(),
			leagueParticipant.memberToken(),
			leagueParticipant.createdAt(), leagueParticipant.modifiedAt());
	}
}
