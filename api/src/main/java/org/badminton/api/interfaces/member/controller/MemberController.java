package org.badminton.api.interfaces.member.controller;

import java.util.List;

import org.badminton.api.application.member.MemberFacade;
import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.badminton.api.aws.s3.service.MemberProfileImageService;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.club.dto.ClubCardResponse;
import org.badminton.api.interfaces.club.dto.CustomPageResponse;
import org.badminton.api.interfaces.match.dto.MatchResultResponse;
import org.badminton.api.interfaces.member.MemberDtoMapper;
import org.badminton.api.interfaces.member.dto.MemberMyPageResponse;
import org.badminton.api.interfaces.member.dto.MemberUpdateRequest;
import org.badminton.api.interfaces.member.dto.MemberUpdateResponse;
import org.badminton.api.interfaces.member.dto.SimpleMemberResponse;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.member.info.MemberMyPageInfo;
import org.badminton.domain.domain.member.info.MemberUpdateInfo;
import org.badminton.domain.domain.member.info.SimpleMemberInfo;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	private static final String DEFAULT_PAGE_VALUE = "0";
	private static final String DEFAULT_SIZE_VALUE = "9";
	private final MemberProfileImageService memberProfileImageService;
	private final MemberDtoMapper memberDtoMapper;
	private final MemberFacade memberFacade;

	@Operation(
		summary = "프로필 사진과 이름을 수정합니다",
		description = """
			프로필 사진과 이름을 수정합니다. 다음 조건을 만족해야 합니다:
			
			1. 프로필 이미지 URL:
			   - 호스트: badminton-team.s3.ap-northeast-2.amazonaws.com
			   - 경로: /member-profile/로 시작
			   - 파일 확장자: png, jpg, jpeg, gif 중 하나""",
		tags = {"Member"}
	)
	@PutMapping()
	public CommonResponse<MemberUpdateResponse> updateProfile(
		@Valid @RequestBody MemberUpdateRequest request,
		@AuthenticationPrincipal CustomOAuth2Member member) {
		MemberUpdateInfo memberUpdateInfo = memberFacade.updateProfile(member.getMemberToken(),
			request.profileImageUrl(), request.name());
		MemberUpdateResponse memberUpdateResponse = memberDtoMapper.of(memberUpdateInfo);
		return CommonResponse.success(memberUpdateResponse);
	}

	@Operation(
		summary = "회원 정보를 조회합니다",
		description = "회원의 마이페이지 접근 시 정보 조회 (동호회 정보 포함)",
		tags = {"Member"}
	)
	@GetMapping("/myPage")
	public CommonResponse<MemberMyPageResponse> getMemberInfo(@AuthenticationPrincipal CustomOAuth2Member member) {
		MemberMyPageInfo memberMyPageInfo = memberFacade.getMemberMyPageInfo(member.getMemberToken());
		MemberMyPageResponse memberMyPageResponse = MemberMyPageResponse.from(memberMyPageInfo);
		return CommonResponse.success(memberMyPageResponse);
	}

	@Operation(
		summary = "로그인 세션을 확인합니다.",
		description = "로그인 세션을 확인하여 로그인 여부를 판단할 때 사용합니다.",
		tags = {"Member"}
	)
	@GetMapping("/session")
	public CommonResponse<SimpleMemberResponse> getMySummaryInfo(@AuthenticationPrincipal CustomOAuth2Member member
	) {
		SimpleMemberInfo simpleMemberInfo = memberFacade.getSimpleMember(member.getMemberToken());
		SimpleMemberResponse simpleMemberResponse = memberDtoMapper.of(simpleMemberInfo);
		return CommonResponse.success(simpleMemberResponse);
	}

	@GetMapping("/matchesRecord")
	@Operation(summary = "회원 경기 기록 조회",
		description = "회원 경기 기록 조회.",
		tags = {"Member"})
	public CommonResponse<CustomPageResponse<MatchResultResponse>> readMemberLeagueRecord(
		@AuthenticationPrincipal CustomOAuth2Member member,
		@RequestParam(defaultValue = DEFAULT_PAGE_VALUE) int page,
		@RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size
	) {
		Page<MatchResultResponse> matchResults =
			memberFacade.getMemberMatchResults(member.getMemberToken(), page, size);
		CustomPageResponse<MatchResultResponse> pageResponse = new CustomPageResponse<>(matchResults);

		return CommonResponse.success(pageResponse);
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
		@ModelAttribute ImageUploadRequest request) {
		return CommonResponse.success(memberFacade.saveImage(request));
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

