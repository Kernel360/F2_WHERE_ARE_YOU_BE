package org.badminton.api.interfaces.club.dto;

import org.badminton.domain.domain.club.command.ApplyClubCommand;

public record ApplyClubRequest(
	String applyReason
) {
	public ApplyClubCommand of() {
		return new ApplyClubCommand(this.applyReason);
	}
}
