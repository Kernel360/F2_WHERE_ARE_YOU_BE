package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record ClubMemberInfo(
	Long clubMemberId,
	String image,
	String name,
	ClubMember.ClubMemberRole role
) {
	public static ClubMemberInfo valueOf(ClubMember clubMember) {
		return new ClubMemberInfo(
			clubMember.getClubMemberId(),
			clubMember.getMember().getProfileImage(),
			clubMember.getMember().getName(),
			clubMember.getRole()
		);
	}
}
