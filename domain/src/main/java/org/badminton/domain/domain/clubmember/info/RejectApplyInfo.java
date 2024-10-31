package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.club.entity.ClubApply;

public record RejectApplyInfo(
	Long clubApplyId,
	ClubApply.ApplyStatus status
) {
	public static RejectApplyInfo fromClubApply(ClubApply clubApply) {
		return new RejectApplyInfo(
			clubApply.getClubApplyId(),
			clubApply.getStatus()
		);
	}
}
