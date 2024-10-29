package org.badminton.domain.domain.member.info;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record MemberIsClubMemberInfo(
	boolean isClubMember,
	ClubMember.ClubMemberRole role,
	Long clubId
) {


}
