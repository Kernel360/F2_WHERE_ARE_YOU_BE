package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record MemberIsClubMemberInfo(
	boolean isClubMember,
	ClubMember.ClubMemberRole role
) {
	public static MemberIsClubMemberInfo fromClubMember(boolean isClubMember, ClubMember clubMember) {
		ClubMember.ClubMemberRole role = (clubMember != null) ? clubMember.getRole() : null;
		return new MemberIsClubMemberInfo(isClubMember, role);
	}

}
