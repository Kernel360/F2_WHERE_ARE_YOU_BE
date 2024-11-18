package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

public record Participant(

	@Schema(description = "참가자 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
	String memberToken,

	@Schema(description = "참가자 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String name,

	@Schema(description = "참가자 이미지", requiredMode = Schema.RequiredMode.REQUIRED)
	String image,

	@Schema(description = "참가자 티어", requiredMode = Schema.RequiredMode.REQUIRED)
	Member.MemberTier tier,

	@Schema(description = "이긴 세트수", requiredMode = Schema.RequiredMode.REQUIRED)
	int participantWinSetCount

) {
}
