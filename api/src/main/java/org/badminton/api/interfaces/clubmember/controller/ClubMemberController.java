package org.badminton.api.interfaces.clubmember.controller;

import org.badminton.api.application.clubMember.ClubMemberFacade;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.club.dto.ClubApplyRequest;
import org.badminton.api.interfaces.club.dto.CustomPageResponse;
import org.badminton.api.interfaces.clubmember.ClubMemberDtoMapper;
import org.badminton.api.interfaces.clubmember.dto.ApproveApplyResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubApplyResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberBanRecordResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberBanRequest;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberExpelRequest;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberRoleUpdateRequest;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberWithdrawResponse;
import org.badminton.api.interfaces.clubmember.dto.MemberIsClubMemberResponse;
import org.badminton.api.interfaces.clubmember.dto.RejectApplyResponse;
import org.badminton.domain.domain.club.command.ClubApplyCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberBanCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberExpelCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberRoleUpdateCommand;
import org.badminton.domain.domain.clubmember.info.ApplyClubInfo;
import org.badminton.domain.domain.clubmember.info.ApproveApplyInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberWithdrawInfo;
import org.badminton.domain.domain.clubmember.info.MemberIsClubMemberInfo;
import org.badminton.domain.domain.clubmember.info.RejectApplyInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/clubs/{clubToken}/clubMembers")
public class ClubMemberController {

	private static final String DEFAULT_PAGE_VALUE = "0";
	private static final String DEFAULT_SIZE_VALUE = "9";

	private final ClubMemberFacade clubMemberFacade;
	private final ClubMemberDtoMapper clubMemberDtoMapper;

	@Operation(summary = "동호회 회원 전체 조회",
		description = "동호회에 가입한 회원들의 리스트를 조회합니다. 제재된 회원은 제외하고 조회합니다.",
		tags = {"ClubMember"})
	@GetMapping
	public CommonResponse<CustomPageResponse<ClubMemberResponse>> getClubMembersInClub(
		@RequestParam(defaultValue = DEFAULT_PAGE_VALUE) int page,
		@RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size,
		@PathVariable String clubToken,
		@AuthenticationPrincipal CustomOAuth2Member member
	) {
		Page<ClubMemberInfo> clubMembers = clubMemberFacade.findAllActiveClubMembers(member.getMemberToken(), clubToken,
			PageRequest.of(page, size));
		Page<ClubMemberResponse> clubMemberResponses = clubMemberDtoMapper.of(clubMembers);
		return CommonResponse.success(new CustomPageResponse<>(clubMemberResponses));
	}

	@Operation(summary = "동호회에서 제재된 회원들을 조회",
		description = "동호회에서 제재된 회원들의 리스트를 조회합니다.",
		tags = {"ClubMember"})
	@GetMapping("/ban")
	public CommonResponse<CustomPageResponse<ClubMemberResponse>> getBannedClubMember(
		@RequestParam(defaultValue = DEFAULT_PAGE_VALUE) int page,
		@RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size,
		@PathVariable String clubToken,
		@AuthenticationPrincipal CustomOAuth2Member member
	) {
		Sort sort = Sort.by(Sort.Order.by("role"));
		Page<ClubMemberInfo> clubMembers = clubMemberFacade.findAllBannedClubMembers(member.getMemberToken(), clubToken,
			PageRequest.of(page, size, sort));
		Page<ClubMemberResponse> clubMemberResponses = clubMemberDtoMapper.of(clubMembers);
		return CommonResponse.success(new CustomPageResponse<>(clubMemberResponses));
	}

	@Operation(summary = "동호회 가입 신청",
		description = """
			동호회에 가입을 신청합니다.
						
			1. 가입 신청 글 2 ~ 20자
						
			""",
		tags = {"ClubMember"})
	@PostMapping
	public CommonResponse<ClubApplyResponse> applyClub(@AuthenticationPrincipal CustomOAuth2Member member,
		@PathVariable String clubToken, @Valid @RequestBody ClubApplyRequest request) {
		String memberToken = member.getMemberToken();
		ClubApplyCommand command = request.of();
		ApplyClubInfo applyClubInfo = clubMemberFacade.applyClub(memberToken, clubToken, command);
		ClubApplyResponse clubApplyResponse = clubMemberDtoMapper.of(applyClubInfo);
		return CommonResponse.success(clubApplyResponse);
	}

	@Operation(summary = "동호회 가입 승인",
		description = "동호회에 가입을 승인합니다.",
		tags = {"ClubMember"})
	@PostMapping("/approve")
	public CommonResponse<ApproveApplyResponse> approvedClub(@RequestParam Long clubApplyId,
		@PathVariable String clubToken, @AuthenticationPrincipal CustomOAuth2Member member) {
		ApproveApplyInfo approveApplyInfo = clubMemberFacade.approveApplying(clubApplyId, member.getMemberToken(),
			clubToken);
		ApproveApplyResponse approveApplyResponse = clubMemberDtoMapper.of(approveApplyInfo);

		return CommonResponse.success(approveApplyResponse);
	}

	@Operation(summary = "동호회 가입 거부",
		description = "동호회에 가입을 거부합니다.",
		tags = {"ClubMember"})
	@PostMapping("/reject")
	public CommonResponse<RejectApplyResponse> rejectClub(@RequestParam Long clubApplyId,
		@PathVariable String clubToken, @AuthenticationPrincipal CustomOAuth2Member member) {
		RejectApplyInfo rejectApplyInfo = clubMemberFacade.rejectApplying(clubApplyId, member.getMemberToken(),
			clubToken);
		RejectApplyResponse rejectApplyResponse = clubMemberDtoMapper.of(rejectApplyInfo);
		return CommonResponse.success(rejectApplyResponse);
	}

	@Operation(
		summary = "동호회원 역할 변경시키기",
		description = """
			동호회원의 역할을 변경시킵니다. 다음 제약 사항과 정보를 반드시 확인해야 합니다:
						
			1. 회원 역할:
			   - 탈퇴 대상 회원의 현재 역할을 나타냅니다.
			   - 다음 중 하나여야 합니다:
			     * ROLE_MANAGER: 동호회 관리자
			     * ROLE_USER: 일반 회원
			     * ROLE_OWNER: 동호회 회장
			주의사항:\s
			- 동호회 회장으로 역할을 변경시키면 이전의 동호회 회장의 역할은 ROLE_USER 가 됩니다.""",
		tags = {"ClubMember"}
	)
	@PatchMapping("/role")
	public CommonResponse<ClubMemberResponse> updateClubMemberRole(
		@Valid @RequestBody ClubMemberRoleUpdateRequest request,
		@RequestParam Long clubMemberId, @PathVariable String clubToken,
		@AuthenticationPrincipal CustomOAuth2Member member) {

		ClubMemberRoleUpdateCommand clubMemberRoleUpdateCommand = request.of();
		ClubMemberInfo clubMemberInfo = clubMemberFacade.updateClubMemberRole(clubMemberRoleUpdateCommand,
			clubMemberId, clubToken, member.getMemberToken());
		ClubMemberResponse response = clubMemberDtoMapper.of(clubMemberInfo);
		return CommonResponse.success(response);
	}

	@Operation(
		summary = "동호회원 강제 탈퇴시키기",
		description = """
			동호회원을 강제로 탈퇴시킵니다. 다음 제약 사항을 반드시 준수해야 합니다:
						
			1. 회원 제제 사유:
			   - 필수 입력 항목입니다.
			   - 최소 2자 이상이어야 합니다.
			   - 최대 100자 이하여야 합니다.
			2. 자기자신은 탈퇴 시킬 수 없습니다.
						
			""",
		tags = {"ClubMember"}
	)
	@PatchMapping("/expel")
	public CommonResponse<ClubMemberBanRecordResponse> expelClubMember(
		@RequestParam Long clubMemberId,
		@PathVariable String clubToken,
		@Valid @RequestBody ClubMemberExpelRequest request,
		@AuthenticationPrincipal CustomOAuth2Member member
	) {
		ClubMemberExpelCommand clubMemberExpelCommand = request.of();

		ClubMemberBanRecordInfo clubMemberBanRecordInfo = clubMemberFacade.expelClubMember(clubMemberExpelCommand,
			clubMemberId, member.getMemberToken(), clubToken);
		ClubMemberBanRecordResponse clubMemberBanRecordResponse = clubMemberDtoMapper.of(clubMemberBanRecordInfo);
		return CommonResponse.success(clubMemberBanRecordResponse);
	}

	@Operation(
		summary = "동호회원 정지시키기",
		description = """
			동호회원을 정지시킵니다. 다음 제약 사항을 반드시 준수해야 합니다:
						
			1. 회원 제제 사유:
			   - 필수 입력 항목입니다.
			   - 최소 2자 이상이어야 합니다.
			   - 최대 100자 이하여야 합니다.
			2. 정지 유형:
			   - 필수 선택 항목입니다.\\n" +
			   - 다음 중 하나를 입력해야 합니다:\\n" +
			     THREE_DAYS: 3일 정지
			     SEVEN_DAYS: 7일 정지
			     TWO_WEEKS: 14일 정지
						
			""",
		tags = {"ClubMember"}
	)
	@PatchMapping("/ban")
	public CommonResponse<ClubMemberBanRecordResponse> banClubMember(@RequestParam Long clubMemberId,
		@PathVariable String clubToken,
		@Valid @RequestBody ClubMemberBanRequest request, @AuthenticationPrincipal CustomOAuth2Member member) {

		ClubMemberBanCommand clubMemberBanCommand = request.of();
		ClubMemberBanRecordInfo clubMemberBanRecordInfo = clubMemberFacade.banClubMember(clubMemberBanCommand,
			clubMemberId, member.getMemberToken(), clubToken);
		ClubMemberBanRecordResponse clubMemberBanRecordResponse = clubMemberDtoMapper.of(clubMemberBanRecordInfo);
		return CommonResponse.success(clubMemberBanRecordResponse);

	}

	@Operation(
		summary = "동호회에서 탈퇴하기",
		description = "동호회에서 탈퇴하기",
		tags = {"ClubMember"}
	)
	@DeleteMapping
	public CommonResponse<ClubMemberWithdrawResponse> withdrawMember(@AuthenticationPrincipal CustomOAuth2Member member,
		@PathVariable String clubToken) {
		String memberToken = member.getMemberToken();
		ClubMemberInfo clubMemberInfo = clubMemberFacade.getClubMember(memberToken, clubToken);
		ClubMemberWithdrawInfo clubMemberWithdrawInfo = clubMemberFacade.withdrawClubMember(
			clubMemberInfo.clubMemberId(), clubToken);
		ClubMemberWithdrawResponse clubMemberWithdrawResponse = clubMemberDtoMapper.of(clubMemberWithdrawInfo);

		return CommonResponse.success(clubMemberWithdrawResponse);
	}

	@Operation(summary = "동호회 회원인지 조회",
		description = "동호회에 가입한 회원인지 조회.",
		tags = {"ClubMember"})
	@GetMapping("/check")
	public CommonResponse<MemberIsClubMemberResponse> checkIsClubMember(
		@AuthenticationPrincipal CustomOAuth2Member member,
		@PathVariable String clubToken) {
		String memberToken = member.getMemberToken();
		MemberIsClubMemberInfo memberIsClubMemberInfo = clubMemberFacade.checkClubMember(memberToken, clubToken);
		MemberIsClubMemberResponse memberIsClubMemberResponse = clubMemberDtoMapper.of(memberIsClubMemberInfo);

		return CommonResponse.success(memberIsClubMemberResponse);

	}

}

