package org.badminton.domain.domain.club.command;

public record ClubUpdateCommand(
	String clubName,
	String clubDescription,
	String clubImage
) {
}
