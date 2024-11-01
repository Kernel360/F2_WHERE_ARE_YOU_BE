package org.badminton.api.interfaces.club.dto;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.member.entity.Member;

public record ClubApplicantResponse(
	Long clubApplyId,
	String name,
	Member.MemberTier tier,
	String applyReason,
	ClubApply.ApplyStatus status
) {
	public static ClubApplicantResponse fromClubApply(ClubApply clubApply) {
		return new ClubApplicantResponse(
			clubApply.getClubApplyId(),
			clubApply.getMember().getName(),
			clubApply.getMember().getTier(),
			clubApply.getApplyReason(),
			clubApply.getStatus()
		);
	}
}
