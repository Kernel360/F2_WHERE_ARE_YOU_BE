package org.badminton.domain.domain.member.service;

import java.util.List;

import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.member.info.MemberMyPageInfo;
import org.badminton.domain.domain.member.info.MemberUpdateInfo;

public interface MemberService {

	MemberMyPageInfo getMemberInfo(String memberToken, LeagueRecordInfo leagueRecordInfo,
		List<ClubMemberMyPageInfo> clubMemberMyPageInfos);

	MemberUpdateInfo updateProfileImage(String memberToken, String imageUrl);
}
