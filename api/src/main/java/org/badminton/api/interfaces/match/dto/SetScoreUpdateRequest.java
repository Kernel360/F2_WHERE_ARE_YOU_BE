package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.common.consts.Constants;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SetScoreUpdateRequest(
	@NotNull @Min(Constants.SET_SCORE_MIN) @Max(Constants.SET_SCORE_MAX)
	Integer score1,
	@NotNull @Min(Constants.SET_SCORE_MIN) @Max(Constants.SET_SCORE_MAX)
	Integer score2
) {
}
