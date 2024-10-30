package org.badminton.domain.domain.club.command;

public record ApproveRegistrationCommand(
	String clubToken,
	String memberToken
) {
}
