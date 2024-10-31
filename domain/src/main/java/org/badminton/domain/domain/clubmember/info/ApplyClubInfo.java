package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.club.entity.ClubApply;

public record ApplyClubInfo(
	Long clubApplyId,
	ClubApply.ApplyStatus status
) {
	public static ApplyClubInfo fromClubApply(ClubApply clubApply) {
		return new ApplyClubInfo(
			clubApply.getClubApplyId(),
			clubApply.getStatus()
		);
	}
}
