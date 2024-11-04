package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.club.entity.ClubApply;

public record ApplyClubInfo(
	Long clubApplyId,
	String applyReason,
	ClubApply.ApplyStatus status
) {
	public static ApplyClubInfo fromClubApply(ClubApply clubApply) {
		return new ApplyClubInfo(
			clubApply.getClubApplyId(),
			clubApply.getApplyReason(),
			clubApply.getStatus()
		);
	}
}
