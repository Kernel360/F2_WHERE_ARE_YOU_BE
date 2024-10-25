package org.badminton.domain.domain.member.service;

import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.member.info.MemberIsClubMemberInfo;
import org.badminton.domain.domain.member.info.MemberMyPageInfo;
import org.badminton.domain.domain.member.info.MemberUpdateInfo;

public interface MemberService {

	MemberIsClubMemberInfo getMemberIsClubMember(String memberToken, ClubMemberMyPageInfo clubMemberMyPageInfo);

	MemberMyPageInfo getMemberInfo(String memberToken, LeagueRecordInfo leagueRecordInfo,
		ClubMemberMyPageInfo clubMemberMyPageInfo);

	MemberUpdateInfo updateProfileImage(String memberToken, String imageUrl);
}
