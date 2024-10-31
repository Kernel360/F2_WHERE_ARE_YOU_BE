package org.badminton.domain.domain.club.command;

public record RejectRegistrationCommand(
	String clubToken,
	String memberToken
) {
}
