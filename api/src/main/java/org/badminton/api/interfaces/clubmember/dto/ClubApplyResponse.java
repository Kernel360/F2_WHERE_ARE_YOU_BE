package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.info.ClubApplyInfo;

public record ClubApplyResponse(
	Long clubApplyId,
	String applyReason,
	ClubApply.ApplyStatus status
) {
	public static ClubApplyResponse fromClubApplyInfo(ClubApplyInfo clubApplyInfo) {
		return new ClubApplyResponse(
			clubApplyInfo.clubApplyId(),
			clubApplyInfo.applyReason(),
			clubApplyInfo.status()
		);
	}
}