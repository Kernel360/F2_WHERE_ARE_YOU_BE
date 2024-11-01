package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.club.entity.ClubApply;

public record ClubApplyInfo(
	Long clubApplyId,
	String applyReason,
	ClubApply.ApplyStatus status
) {
	public static ClubApplyInfo fromClubApply(ClubApply clubApply) {
		return new ClubApplyInfo(
			clubApply.getClubApplyId(),
			clubApply.getApplyReason(),
			clubApply.getStatus()
		);
	}
}
