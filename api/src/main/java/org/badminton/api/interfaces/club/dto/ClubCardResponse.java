package org.badminton.api.interfaces.club.dto;

import java.time.LocalDateTime;

public record ClubCardResponse(
	String clubToken,
	String clubName,
	String clubDescription,
	String clubImage,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	Long goldClubMemberCount,
	Long silverClubMemberCount,
	Long bronzeClubMemberCount
) {

}
