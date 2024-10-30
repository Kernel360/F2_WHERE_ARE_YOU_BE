package org.badminton.domain.domain.member.service;

import java.util.List;

import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.member.MemberReader;
import org.badminton.domain.domain.member.MemberStore;
import org.badminton.domain.domain.member.entity.Member;
import org.badminton.domain.domain.member.info.MemberMyPageInfo;
import org.badminton.domain.domain.member.info.MemberUpdateInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberReader memberReader;
	private final MemberStore memberStore;

	// @Override
	// public MemberIsClubMemberInfo getMemberIsClubMember(String memberToken,
	// 	List<ClubMemberMyPageInfo> clubMemberMyPageInfos) {
	//
	// 	if (clubMemberMyPageInfos == null) {
	// 		return new MemberIsClubMemberInfo(false, null, null);
	// 	} else {
	// 		ClubMember.ClubMemberRole clubMemberRole = clubMemberMyPageInfo.role();
	// 		Long clubId = clubMemberMyPageInfo.clubId();
	// 		return new MemberIsClubMemberInfo(true, clubMemberRole, clubId);
	// 	}
	// }

	@Override
	public MemberMyPageInfo getMemberInfo(String memberToken, LeagueRecordInfo leagueRecordInfo,
		List<ClubMemberMyPageInfo> clubMemberMyPageInfos) {
		Member member = memberReader.getMember(memberToken);
		return createMemberMyPageInfo(member, clubMemberMyPageInfos, leagueRecordInfo);
	}

	private MemberMyPageInfo createMemberMyPageInfo(Member member, List<ClubMemberMyPageInfo> clubMemberMyPageInfos,
		LeagueRecordInfo leagueRecordInfo) {
		if (!hasClubMember(clubMemberMyPageInfos)) {
			return MemberMyPageInfo.from(member, leagueRecordInfo);
		}

		return MemberMyPageInfo.from(member, leagueRecordInfo, clubMemberMyPageInfos);
	}

	private boolean hasClubMember(List<ClubMemberMyPageInfo> clubMemberMyPageInfos) {
		return clubMemberMyPageInfos != null;
	}

	@Override
	@Transactional
	public MemberUpdateInfo updateProfileImage(String memberToken, String imageUrl) {
		Member member = memberReader.getMember(memberToken);
		member.updateMember(imageUrl);
		memberStore.store(member);
		return MemberUpdateInfo.fromMemberEntity(member);
	}

}
