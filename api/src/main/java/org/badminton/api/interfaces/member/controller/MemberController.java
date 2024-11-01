package org.badminton.api.interfaces.member.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.badminton.api.application.match.MyMatchFacade;
import org.badminton.api.application.member.MemberFacade;
import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.badminton.api.aws.s3.service.MemberProfileImageService;
import org.badminton.api.common.exception.member.ImageFileNotFoundException;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.club.dto.ClubCardResponse;
import org.badminton.api.interfaces.match.dto.MatchResultResponse;
import org.badminton.api.interfaces.member.MemberDtoMapper;
import org.badminton.api.interfaces.member.dto.MemberMyPageResponse;
import org.badminton.api.interfaces.member.dto.MemberUpdateRequest;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.match.info.MatchResultInfo;
import org.badminton.domain.domain.member.info.MemberMyPageInfo;
import org.badminton.domain.domain.member.info.MemberUpdateInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/members")
@Slf4j
public class MemberController {

	private final MemberProfileImageService memberProfileImageService;
	private final MemberDtoMapper memberDtoMapper;
	private final MyMatchFacade myMatchFacade;
	private final MemberFacade memberFacade;

	@Operation(
		summary = "프로필 사진을 수정합니다",
		description = """
			프로필 사진을 수정합니다. 다음 조건을 만족해야 합니다:
			
			1. 프로필 이미지 URL:
			   - 호스트: badminton-team.s3.ap-northeast-2.amazonaws.com
			   - 경로: /member-profile/로 시작
			   - 파일 확장자: png, jpg, jpeg, gif 중 하나""",
		tags = {"Member"}
	)
	@PutMapping("/profileImage")
	public CommonResponse<MemberUpdateInfo> updateProfileImage(
		@Valid @RequestBody MemberUpdateRequest request,
		@AuthenticationPrincipal CustomOAuth2Member member) {
		return CommonResponse.success(
			memberFacade.updateProfileImage(member.getMemberToken(), request.profileImageUrl()));
	}

	@Operation(
		summary = "회원 정보를 조회합니다",
		description = "회원의 마이페이지 접근 시 정보 조회 (동호회 정보 포함)",
		tags = {"Member"}
	)
	@GetMapping("/myPage")
	public CommonResponse<MemberMyPageResponse> getMemberInfo(@AuthenticationPrincipal CustomOAuth2Member member) {
		MemberMyPageInfo memberMyPageInfo = memberFacade.getMemberMyPageInfo(member.getMemberToken());
		MemberMyPageResponse memberMyPageResponse = MemberMyPageResponse.toMemberMyPageResponse(memberMyPageInfo);
		return CommonResponse.success(memberMyPageResponse);
	}

	@GetMapping("/matchesRecord")
	@Operation(summary = "회원 경기 기록 조회",
		description = "회원 경기 기록 조회.",
		tags = {"Member"})
	public CommonResponse<List<MatchResultResponse>> readMemberLeagueRecord(
		@AuthenticationPrincipal CustomOAuth2Member member
	) {
		List<ClubMemberMyPageInfo> clubMembersMyPageInfos = memberFacade.getClubMembers(member.getMemberToken());

		if (clubMembersMyPageInfos.isEmpty()) {
			return CommonResponse.success(Collections.EMPTY_LIST);
		}

		List<MatchResultResponse> allMatchResults = new ArrayList<>();

		for (ClubMemberMyPageInfo clubMemberMyPageInfo : clubMembersMyPageInfos) {
			Long clubMemberId = clubMemberMyPageInfo.clubMemberId();
			List<MatchResultInfo> myMatch = myMatchFacade.getMyMatch(clubMemberId);
			allMatchResults.addAll(myMatch.stream()
				.map(MatchResultResponse::from)
				.toList());
		}
		allMatchResults.sort(Comparator.comparing(MatchResultResponse::leagueAt));

		return CommonResponse.success(allMatchResults);

	}

	@Operation(
		summary = "프로필 사진을 S3에 업로드 합니다",
		description = "프로필 사진을 S3에 업로드합니다",
		tags = {"Member"},
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @Content(
				mediaType = "multipart/form-data",
				schema = @Schema(implementation = ImageUploadRequest.class)
			)
		)
	)
	@PostMapping("/profileImage")
	public CommonResponse<String> uploadProfileImage(
		@RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile,
		@AuthenticationPrincipal CustomOAuth2Member member) {
		if (multipartFile == null || multipartFile.isEmpty()) {
			throw new ImageFileNotFoundException();
		}
		ImageUploadRequest request = new ImageUploadRequest(multipartFile);
		return CommonResponse.success(memberProfileImageService.uploadFile(request, member.getMemberToken()));
	}

	@Operation(
		summary = "회원이 속한 동호회를 볼 수 있습니다",
		description = "회원이 속한 동호회를 볼 수 있습니다",
		tags = {"Member"}
	)
	@GetMapping("/myClubs")
	public CommonResponse<List<ClubCardResponse>> getMyClubs(@AuthenticationPrincipal CustomOAuth2Member member
	) {
		List<ClubCardInfo> clubCardInfos = memberFacade.getMyClubs(member.getMemberToken());
		List<ClubCardResponse> clubCardResponses = memberDtoMapper.of(clubCardInfos);
		return CommonResponse.success(clubCardResponses);
	}
}

