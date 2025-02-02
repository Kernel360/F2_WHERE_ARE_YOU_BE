package org.badminton.domain.domain.member.info;

import java.util.List;

import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.member.entity.Member;

public record MemberMyPageInfo(
	String memberToken,

	String name,

	String email,

	String profileImage,

	Member.MemberTier tier,

	int winCount,

	int loseCount,

	int drawCount,

	int matchCount,

	List<ClubMemberMyPageInfo> clubMemberMyPageInfos
) {
	public static MemberMyPageInfo from(Member member, LeagueRecordInfo leagueRecordInfo,
		List<ClubMemberMyPageInfo> clubMemberMyPageInfos) {
		return new MemberMyPageInfo(
			member.getMemberToken(), member.getName(), member.getEmail(), member.getProfileImage(), member.getTier(),
			leagueRecordInfo.winCount(), leagueRecordInfo.loseCount(), leagueRecordInfo.drawCount(),
			leagueRecordInfo.matchCount(), clubMemberMyPageInfos);

	}

	public static MemberMyPageInfo from(Member member, LeagueRecordInfo leagueRecordInfo) {
		return new MemberMyPageInfo(
			member.getMemberToken(), member.getName(), member.getEmail(), member.getProfileImage(), member.getTier(),
			leagueRecordInfo.winCount(), leagueRecordInfo.loseCount(), leagueRecordInfo.drawCount(),
			leagueRecordInfo.matchCount(), null);
	}
}
