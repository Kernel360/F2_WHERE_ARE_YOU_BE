package org.badminton.domain.domain.club.command;

import org.badminton.domain.domain.club.Club;

public record ClubCreateCommand(
	String clubName,
	String clubDescription,
	String clubImage
) {
	public Club toEntity() {
		return new Club(this.clubName, this.clubDescription, this.clubImage);
	}
}
