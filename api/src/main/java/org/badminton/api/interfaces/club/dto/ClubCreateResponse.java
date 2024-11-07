package org.badminton.api.interfaces.club.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClubCreateResponse(
	@Schema(description = "동호회 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubToken,
	@Schema(description = "동호회 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubName,
	@Schema(description = "동호회 설명", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubDescription,
	@Schema(description = "동호회 이미지 url", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubImage,
	@Schema(description = "동호회 생성 일자", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime createdAt,
	@Schema(description = "동호회 수정 일자", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime modifiedAt
) {
}
