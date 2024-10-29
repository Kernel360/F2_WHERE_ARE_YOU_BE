package org.badminton.api.interfaces.club.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.badminton.api.interfaces.club.validation.ClubDescriptionValidator;
import org.badminton.api.interfaces.club.validation.ClubImageValidator;
import org.badminton.api.interfaces.club.validation.ClubNameValidator;

@Schema(description = "동호회 생성 요청")
public record ClubCreateRequest(

        @ClubNameValidator
        @Schema(description = "동호회 이름", example = "멋있는 동호회")
        String clubName,

        @ClubDescriptionValidator
        @Schema(description = "동호회 설명", example = "배드민턴을 즐겁게 치는 즐거운 멋진 동호회입니다.")
        String clubDescription,

        @ClubImageValidator
        @Schema(description = "동호회 이미지 URL", example = "https://d36om9pjoifd2y.cloudfront.net/club-banner/3257bbf2-52dc-41b2-9d1f-a6c66f3c7216.avif")
        String clubImage
) {
}