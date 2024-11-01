package org.badminton.domain.domain.club.command;

public record ClubApplyCommand(
	String applyReason
) {
	public ClubApplyCommand of() {
		return new ClubApplyCommand(this.applyReason);
	}
}
