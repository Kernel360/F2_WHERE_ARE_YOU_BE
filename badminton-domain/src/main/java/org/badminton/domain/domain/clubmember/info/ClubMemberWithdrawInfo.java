package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record ClubMemberWithdrawInfo(
	Long clubId,
	Long clubMemberId,
	boolean isDeleted
) {
	public static ClubMemberWithdrawInfo from(ClubMember clubMember) {
		return new ClubMemberWithdrawInfo(
			clubMember.getClub().getClubId(),
			clubMember.getClubMemberId(),
			clubMember.isDeleted()
		);
	}
}
