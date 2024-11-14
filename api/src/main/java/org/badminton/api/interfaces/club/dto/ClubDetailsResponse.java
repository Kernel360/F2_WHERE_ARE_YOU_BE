package org.badminton.api.interfaces.club.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClubDetailsResponse(
	@Schema(description = "동호회 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubToken,
	@Schema(description = "동호회 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubName,
	@Schema(description = "동호회 설명", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubDescription,
	@Schema(description = "동호회 이미지 url", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubImage,
	@Schema(description = "동호회의 골드티어 수", requiredMode = Schema.RequiredMode.REQUIRED)
	Long goldClubMemberCount,
	@Schema(description = "동호회 실버티어 수", requiredMode = Schema.RequiredMode.REQUIRED)
	Long silverClubMemberCount,
	@Schema(description = "동호회 브론즈티어 수", requiredMode = Schema.RequiredMode.REQUIRED)
	Long bronzeClubMemberCount,
	@Schema(description = "동호회에 속한 맴버 수", requiredMode = Schema.RequiredMode.REQUIRED)
	int clubMemberCount,
	@Schema(description = "동호회 생성 날짜", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime createdAt
) {

}
