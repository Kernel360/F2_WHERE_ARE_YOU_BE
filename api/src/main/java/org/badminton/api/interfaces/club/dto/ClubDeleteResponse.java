package org.badminton.api.interfaces.club.dto;

import org.badminton.domain.domain.club.entity.Club;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClubDeleteResponse(
	@Schema(description = "동호회 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubToken,
	@Schema(description = "동호회 삭제 여부", requiredMode = Schema.RequiredMode.REQUIRED)
	boolean isClubDeleted
) {

	public static ClubDeleteResponse clubEntityToClubDeleteResponse(Club club) {
		return new ClubDeleteResponse(club.getClubToken(), club.isClubDeleted());
	}
}
