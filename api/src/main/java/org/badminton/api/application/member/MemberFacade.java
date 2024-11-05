package org.badminton.api.application.member;

import java.util.List;

import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.clubmember.service.ClubMemberService;
import org.badminton.domain.domain.league.entity.LeagueRecord;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.league.service.LeagueRecordService;
import org.badminton.domain.domain.member.info.MemberMyPageInfo;
import org.badminton.domain.domain.member.info.MemberUpdateInfo;
import org.badminton.domain.domain.member.info.SimpleMemberInfo;
import org.badminton.domain.domain.member.service.MemberService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacade {

	private final MemberService memberService;
	private final LeagueRecordService leagueRecordService;
	private final ClubMemberService clubMemberService;

	public MemberMyPageInfo getMemberMyPageInfo(String memberToken) {
		List<ClubMemberMyPageInfo> clubMemberMyPageInfos = clubMemberService.getClubMembers(memberToken);
		LeagueRecord leagueRecord = leagueRecordService.getLeagueRecord(memberToken);
		LeagueRecordInfo leagueRecordInfo = LeagueRecordInfo.from(leagueRecord);
		return memberService.getMemberInfo(memberToken, leagueRecordInfo, clubMemberMyPageInfos);
	}

	public SimpleMemberInfo getSimpleMember(String memberToken) {
		return memberService.getSimpleMember(memberToken);
	}

	public List<ClubMemberMyPageInfo> getClubMembers(String memberToken) {
		return clubMemberService.getClubMembers(memberToken);
	}

	public MemberUpdateInfo updateProfileImage(String memberToken, String imageUrl) {
		return memberService.updateProfileImage(memberToken, imageUrl);
	}

	public List<ClubCardInfo> getMyClubs(String memberToken) {
		return clubMemberService.getClubsByMemberToken(memberToken);
	}
}
