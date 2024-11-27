package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record MemberIsClubMemberInfo(
	boolean isClubMember,
	ClubMember.ClubMemberRole role,
	boolean isBanned
) {
	public static MemberIsClubMemberInfo from(boolean isClubMember, ClubMember clubMember, boolean isBanned) {
		ClubMember.ClubMemberRole role = (clubMember != null) ? clubMember.getRole() : null;
		return new MemberIsClubMemberInfo(isClubMember, role, isBanned);
	}

}
