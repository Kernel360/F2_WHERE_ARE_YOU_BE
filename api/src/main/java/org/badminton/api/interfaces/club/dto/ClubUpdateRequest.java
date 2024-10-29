package org.badminton.api.interfaces.club.dto;

import org.badminton.api.interfaces.club.validation.ClubDescriptionValidator;
import org.badminton.api.interfaces.club.validation.ClubImageValidator;
import org.badminton.api.interfaces.club.validation.ClubNameValidator;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClubUpdateRequest(
	@Schema(example = "멋쟁이 동호회 아저씨들 모임")
	@ClubNameValidator
	String clubName,

	@Schema(example = "멋쟁이 동호회 아저씨들만 오세요.")
	@ClubDescriptionValidator
	String clubDescription,

	@Schema(example = "https://d36om9pjoifd2y.cloudfront.net/club-banner/3257bbf2-52dc-41b2-9d1f-a6c66f3c7216.avif")
	@ClubImageValidator
	String clubImage
) {

}
