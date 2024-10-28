package org.badminton.api.interfaces.club.dto;

import java.time.LocalDateTime;

public record ClubCreateResponse(
	String clubToken,
	String clubName,
	String clubDescription,
	String clubImage,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
}
