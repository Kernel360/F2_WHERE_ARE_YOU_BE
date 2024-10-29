package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberWithdrawInfo;

public record ClubMemberWithdrawResponse(
        Long clubId,
        Long clubMemberId,
        boolean isDeleted
) {
	public static ClubMemberWithdrawResponse valueOf(ClubMemberWithdrawInfo info) {
		return new ClubMemberWithdrawResponse(
			info.clubId(),
			info.clubMemberId(),
			info.isDeleted()
		);
	}
}
