package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record ClubMemberMyPageInfo(
	Long clubId,

	Long clubMemberId,

	String clubName,

	ClubMember.ClubMemberRole role

) {
	public static ClubMemberMyPageInfo from(ClubMember clubMember) {
		if (clubMember == null) {
			return null;
		}
		return new ClubMemberMyPageInfo(clubMember.getClub().getClubId(), clubMember.getClubMemberId(),
			clubMember.getClub().getClubName(), clubMember.getRole());
	}

}
