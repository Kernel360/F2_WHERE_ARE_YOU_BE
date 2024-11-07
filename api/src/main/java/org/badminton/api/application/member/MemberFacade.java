package org.badminton.api.application.member;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.badminton.api.interfaces.match.dto.MatchResultResponse;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.clubmember.service.ClubMemberService;
import org.badminton.domain.domain.league.entity.LeagueRecord;
import org.badminton.domain.domain.league.info.LeagueRecordInfo;
import org.badminton.domain.domain.league.service.LeagueRecordService;
import org.badminton.domain.domain.match.info.MatchResultInfo;
import org.badminton.domain.domain.match.service.MatchResultService;
import org.badminton.domain.domain.member.info.MemberMyPageInfo;
import org.badminton.domain.domain.member.info.MemberUpdateInfo;
import org.badminton.domain.domain.member.info.SimpleMemberInfo;
import org.badminton.domain.domain.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacade {
	private static final String SORT_METHOD = "leagueAt";

	private final MemberService memberService;
	private final LeagueRecordService leagueRecordService;
	private final ClubMemberService clubMemberService;
	private final MatchResultService matchResultService;

	@Transactional
	public MemberMyPageInfo getMemberMyPageInfo(String memberToken) {
		List<ClubMemberMyPageInfo> clubMemberMyPageInfos = clubMemberService.getClubMembers(memberToken);
		LeagueRecord leagueRecord = leagueRecordService.getLeagueRecord(memberToken);
		LeagueRecordInfo leagueRecordInfo = LeagueRecordInfo.from(leagueRecord);
		return memberService.getMemberInfo(memberToken, leagueRecordInfo, clubMemberMyPageInfos);
	}

	@Transactional(readOnly = true)
	public SimpleMemberInfo getSimpleMember(String memberToken) {
		return memberService.getSimpleMember(memberToken);
	}

	@Transactional
	public MemberUpdateInfo updateProfile(String memberToken, String imageUrl, String name) {
		return memberService.updateProfile(memberToken, imageUrl, name);
	}

	@Transactional
	public List<ClubCardInfo> getMyClubs(String memberToken) {
		return clubMemberService.getClubsByMemberToken(memberToken);
	}

	@Transactional
	public Page<MatchResultResponse> getMemberMatchResults(String memberToken, int page, int size) {
		List<ClubMemberMyPageInfo> clubMembersMyPageInfos = clubMemberService.getClubMembers(memberToken);
		List<MatchResultResponse> allMatchResults = new ArrayList<>();
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, SORT_METHOD));

		for (ClubMemberMyPageInfo clubMemberMyPageInfo : clubMembersMyPageInfos) {
			Long clubMemberId = clubMemberMyPageInfo.clubMemberId();
			List<MatchResultInfo> myMatch = matchResultService.getAllMatchResultsByClubMember(clubMemberId);
			allMatchResults.addAll(myMatch.stream()
				.map(MatchResultResponse::from)
				.toList());
		}

		allMatchResults.sort(Comparator.comparing(MatchResultResponse::leagueAt).reversed());

		int start = (int)pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), allMatchResults.size());
		return new PageImpl<>(allMatchResults
			.subList(start, end), pageable, allMatchResults.size());
	}
}
