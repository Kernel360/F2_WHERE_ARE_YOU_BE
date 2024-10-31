package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.info.ApplyClubInfo;

public record ApplyClubResponse(
	Long clubApplyId,
	ClubApply.ApplyStatus status
) {
	public static ApplyClubResponse fromClubApplyInfo(ApplyClubInfo applyClubInfo) {
		return new ApplyClubResponse(
			applyClubInfo.clubApplyId(),
			applyClubInfo.status()
		);
	}
}