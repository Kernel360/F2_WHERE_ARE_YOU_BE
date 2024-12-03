package org.badminton.api.application.member;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.badminton.api.aws.s3.event.memeber.MemberImageEvent;
import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.badminton.api.common.exception.member.MemberNotExistException;
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
import org.springframework.context.ApplicationEventPublisher;
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
	private final ApplicationEventPublisher eventPublisher;

	private static final String CLOUDFRONT_URL_PREFIX = "https://d36om9pjoifd2y.cloudfront.net/member-profile/";
	private static final String WEBP = "webp";
	private static final String AVIF = "avif";
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

	@Transactional
	public String saveImage(ImageUploadRequest request, String memberToken) {
		// uuid 객체 생성
		String uuid = UUID.randomUUID().toString();
		String extension = getFileExtension(Objects.requireNonNull(request.multipartFile().getOriginalFilename()));

		if (memberToken == null) {
			throw new MemberNotExistException("멤버 토큰이 없습니다. 로그인을 먼저 해주세요.");
		}
		// 비동기 처리
		eventPublisher.publishEvent(new MemberImageEvent(request.multipartFile(), uuid));

		// url 만드는 곳으로 전달
		return preReturnUrl(uuid, extension);
	}

	private String preReturnUrl(String uuid, String extension) {
		String origin = CLOUDFRONT_URL_PREFIX + uuid + ".";
		if (extension.equals(WEBP) || extension.equals(AVIF)) {
			return origin + extension;
		}
		return origin + WEBP;
	}

	private String getFileExtension(String filename) {
		int dotIndex = filename.lastIndexOf(".");
		if (dotIndex == -1) {
			return "";
		}
		return filename.substring(dotIndex + 1);
	}
}
