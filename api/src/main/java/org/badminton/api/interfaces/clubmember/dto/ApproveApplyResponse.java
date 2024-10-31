package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.info.ApproveApplyInfo;

public record ApproveApplyResponse(
	Long clubApplyId,
	ClubApply.ApplyStatus status
) {
	public static ApproveApplyResponse fromApproveApplyInfo(ApproveApplyInfo approveApplyInfo) {
		return new ApproveApplyResponse(
			approveApplyInfo.clubApplyId(),
			approveApplyInfo.status()
		);
	}
}