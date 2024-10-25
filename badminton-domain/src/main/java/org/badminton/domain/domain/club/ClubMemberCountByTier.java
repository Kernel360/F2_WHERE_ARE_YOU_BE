package org.badminton.domain.domain.club;

import java.util.Map;

import org.badminton.domain.domain.member.entity.Member;

public record ClubMemberCountByTier(
	Long goldClubMemberCount,
	Long silverClubMemberCount,
	Long bronzeClubMemberCount
) {
	public static ClubMemberCountByTier ofClubMemberCountResponse(Map<Member.MemberTier, Long> memberCountByTier) {
		return new ClubMemberCountByTier(memberCountByTier.get(Member.MemberTier.GOLD),
			memberCountByTier.get(Member.MemberTier.SILVER),
			memberCountByTier.get(Member.MemberTier.BRONZE));
	}
}
