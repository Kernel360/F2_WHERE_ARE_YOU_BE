package org.badminton.domain.domain.member.info;

import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.member.entity.Member;

public record MemberMyPageInfo(
	String memberToken,

	String name,

	String email,

	String profileImage,

	Member.MemberTier tier,

	LeagueRecordInfo leagueRecordInfo,

	ClubMemberMyPageInfo clubMemberMyPageInfo

) {
	public static MemberMyPageInfo from(Member member, LeagueRecordInfo leagueRecordInfo,
		ClubMemberMyPageInfo clubMemberMyPageInfo) {
		return new MemberMyPageInfo(
			member.getMemberToken(), member.getName(), member.getEmail(), member.getProfileImage(), member.getTier(),
			leagueRecordInfo, clubMemberMyPageInfo);

	}

	public static MemberMyPageInfo from(Member member, LeagueRecordInfo leagueRecordInfo) {
		return new MemberMyPageInfo(
			member.getMemberToken(), member.getName(), member.getEmail(), member.getProfileImage(), member.getTier(),
			leagueRecordInfo, null);

	}
}
