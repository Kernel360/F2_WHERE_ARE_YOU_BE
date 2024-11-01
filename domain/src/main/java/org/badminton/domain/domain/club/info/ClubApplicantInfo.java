package org.badminton.domain.domain.club.info;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.member.entity.Member;

public record ClubApplicantInfo(
	Long clubApplyId,
	String name,
	Member.MemberTier tier,
	String applyReason,
	ClubApply.ApplyStatus status
) {
	public static ClubApplicantInfo from(ClubApply clubApply) {
		return new ClubApplicantInfo(
			clubApply.getClubApplyId(),
			clubApply.getMember().getName(),
			clubApply.getMember().getTier(),
			clubApply.getApplyReason(),
			clubApply.getStatus()
		);
	}
}
