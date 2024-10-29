package org.badminton.api.interfaces.club.dto;

import java.time.LocalDateTime;

import org.badminton.domain.domain.club.entity.Club;

public record ClubUpdateResponse(
	String clubToken,
	String clubName,
	String clubDescription,
	String clubImage,
	LocalDateTime createdAt,
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
