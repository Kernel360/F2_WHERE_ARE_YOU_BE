package org.badminton.api.interfaces.club.dto;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.club.info.ClubApplicantInfo;
import org.badminton.domain.domain.member.entity.Member;

public record ClubApplicantResponse(
	Long clubApplyId,
	String name,
	Member.MemberTier tier,
	String applyReason,
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
