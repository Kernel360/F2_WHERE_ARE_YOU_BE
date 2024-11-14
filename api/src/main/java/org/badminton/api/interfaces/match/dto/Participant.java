package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

public record Participant(

	@Schema(description = "참가자 토큰")
	String memberToken,

	@Schema(description = "참가자 이름")
	String name,

	@Schema(description = "참가자 이미지")
	String image,

	@Schema(description = "참가자 티어")
	Member.MemberTier tier,

	@Schema(description = "이긴 세트수")
	int participantWinSetCount

) {
}
