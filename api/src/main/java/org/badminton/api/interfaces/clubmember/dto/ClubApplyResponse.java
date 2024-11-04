package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.info.ApplyClubInfo;

public record ClubApplyResponse(
	Long clubApplyId,
	String applyReason,
	ClubApply.ApplyStatus status
) {
	public static ClubApplyResponse fromClubApplyInfo(ApplyClubInfo applyClubInfo) {
		return new ClubApplyResponse(
			applyClubInfo.clubApplyId(),
			applyClubInfo.applyReason(),
			applyClubInfo.status()
		);
	}
}