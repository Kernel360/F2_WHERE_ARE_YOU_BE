package org.badminton.api.interfaces.club.dto;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.club.info.ClubApplicantInfo;
import org.badminton.domain.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClubApplicantResponse(
	@Schema(description = "동호회 가입 고유 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
	Long clubApplyId,
	@Schema(description = "동호회 가입자 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String name,
	@Schema(description = "개인 티어 (GOLD | SILVER | BRONZE", requiredMode = Schema.RequiredMode.REQUIRED)
	Member.MemberTier tier,
	@Schema(description = "가입 사유", requiredMode = Schema.RequiredMode.REQUIRED)
	String applyReason,
	@Schema(description = "동호회 가입 상태 (APPROVED | PENDING | REJECTED", requiredMode = Schema.RequiredMode.REQUIRED)
	ClubApply.ApplyStatus status
) {
	public static ClubApplicantResponse from(ClubApplicantInfo clubApply) {
		return new ClubApplicantResponse(
			clubApply.clubApplyId(),
			clubApply.name(),
			clubApply.tier(),
			clubApply.applyReason(),
			clubApply.status()
		);
	}
}
