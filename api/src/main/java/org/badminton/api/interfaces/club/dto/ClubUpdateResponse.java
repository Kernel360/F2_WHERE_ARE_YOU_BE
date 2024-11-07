package org.badminton.api.interfaces.club.dto;

import java.time.LocalDateTime;

import org.badminton.domain.domain.club.entity.Club;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClubUpdateResponse(
	@Schema(description = "동호회 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubToken,
	@Schema(description = "동호회 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubName,
	@Schema(description = "동호회 설명", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubDescription,
	@Schema(description = "동호회 이미지 url", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubImage,
	@Schema(description = "동호회 생성 날짜", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime createdAt,
	@Schema(description = "동호회 수정 날짜", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime modifiedAt
) {
	public static ClubUpdateResponse fromClubEntity(Club club) {
		return new ClubUpdateResponse(
			club.getClubToken(),
			club.getClubName(),
			club.getClubDescription(),
			club.getClubImage(),
			club.getCreatedAt(),
			club.getModifiedAt()
		);
	}
}
