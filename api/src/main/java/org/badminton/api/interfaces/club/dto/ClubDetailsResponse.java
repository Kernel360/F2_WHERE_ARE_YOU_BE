package org.badminton.api.interfaces.club.dto;

import java.time.LocalDateTime;

public record ClubDetailsResponse(
	String clubToken,
	String clubName,
	String clubDescription,
	String clubImage,
	Long goldClubMemberCount,
	Long silverClubMemberCount,
	Long bronzeClubMemberCount,
	int clubMemberCount,
	LocalDateTime createdAt,
	boolean isClubMember
) {

}
