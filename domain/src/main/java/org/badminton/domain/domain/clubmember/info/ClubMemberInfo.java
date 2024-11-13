package org.badminton.domain.domain.clubmember.info;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.member.entity.Member;

public record ClubMemberInfo(
	Long clubMemberId,
	String image,
	String name,
	ClubMember.ClubMemberRole role,
	LeagueRecordInfo leagueRecord,
	Member.MemberTier tier
) {
	public static ClubMemberInfo valueOf(ClubMember clubMember) {
		return new ClubMemberInfo(
			clubMember.getClubMemberId(),
			clubMember.getMember().getProfileImage(),
			clubMember.getMember().getName(),
			clubMember.getRole(),
			LeagueRecordInfo.from(clubMember.getMember().getLeagueRecord()),
			clubMember.getMember().getTier()
		);
	}
}
