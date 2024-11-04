package org.badminton.api.interfaces.club.dto;

import org.badminton.domain.domain.club.command.ClubApplyCommand;

public record ClubApplyRequest(
	String applyReason
) {
	public ClubApplyCommand of() {
		return new ClubApplyCommand(this.applyReason);
	}
}
