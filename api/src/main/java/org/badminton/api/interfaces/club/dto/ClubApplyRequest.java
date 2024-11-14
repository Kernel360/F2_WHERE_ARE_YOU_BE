package org.badminton.api.interfaces.club.dto;

import org.badminton.domain.common.consts.Constants;
import org.badminton.domain.domain.club.command.ClubApplyCommand;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ClubApplyRequest(
	@NotNull @Min(Constants.APPLY_MIN) @Max(Constants.APPLY_MAX)
	String applyReason
) {
	public ClubApplyCommand of() {
		return new ClubApplyCommand(this.applyReason);
	}
}
