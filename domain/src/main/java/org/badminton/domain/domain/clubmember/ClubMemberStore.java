package org.badminton.domain.domain.clubmember;

import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.member.entity.Member;

public interface ClubMemberStore {
	void store(ClubMember member);

	ClubMember createClubMember(ClubCreateInfo club, Member member, ClubMember.ClubMemberRole role);

}
