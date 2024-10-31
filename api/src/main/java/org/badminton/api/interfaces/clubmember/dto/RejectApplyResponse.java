package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.info.RejectApplyInfo;

public record RejectApplyResponse(
	Long clubApplyId,
	ClubApply.ApplyStatus status
) {
	public static RejectApplyResponse fromRejectApplyInfo(RejectApplyInfo rejectApplyInfo) {
		return new RejectApplyResponse(
			rejectApplyInfo.clubApplyId(),
			rejectApplyInfo.status()
		);
	}
}