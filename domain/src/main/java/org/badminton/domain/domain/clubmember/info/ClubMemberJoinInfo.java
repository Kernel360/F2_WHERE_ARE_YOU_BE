package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record ClubMemberJoinInfo(
	Long clubMemberId,
	String role
) {
	public static ClubMemberJoinInfo from(ClubMember clubMember) {
		return new ClubMemberJoinInfo(clubMember.getClubMemberId(), clubMember.getRole().name());
	}
}
