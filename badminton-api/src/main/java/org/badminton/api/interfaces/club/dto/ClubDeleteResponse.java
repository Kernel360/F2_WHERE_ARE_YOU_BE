package org.badminton.api.interfaces.club.dto;

import org.badminton.domain.domain.club.Club;

public record ClubDeleteResponse(
	Long clubId,
	boolean isClubDeleted
) {

	public static ClubDeleteResponse clubEntityToClubDeleteResponse(Club club) {
		return new ClubDeleteResponse(club.getClubId(), club.isClubDeleted());
	}
}
