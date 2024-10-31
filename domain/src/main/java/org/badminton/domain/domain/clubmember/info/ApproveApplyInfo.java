package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.club.entity.ClubApply;

public record ApproveApplyInfo(
	Long clubApplyId,
	ClubApply.ApplyStatus status
) {
	public static ApproveApplyInfo fromClubApply(ClubApply clubApply) {
		return new ApproveApplyInfo(
			clubApply.getClubApplyId(),
			clubApply.getStatus()
		);
	}
}