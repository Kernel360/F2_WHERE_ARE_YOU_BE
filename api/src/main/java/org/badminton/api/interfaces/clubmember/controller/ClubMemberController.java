package org.badminton.api.interfaces.clubmember.controller;

import java.util.List;
import java.util.Map;

import org.badminton.api.application.clubMember.ClubMemberFacade;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.clubmember.ClubMemberDtoMapper;
import org.badminton.api.interfaces.clubmember.dto.ApplyClubResponse;
import org.badminton.api.interfaces.clubmember.dto.ApproveApplyResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberBanRecordResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberBanRequest;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberExpelRequest;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberResponse;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberRoleUpdateRequest;
import org.badminton.api.interfaces.clubmember.dto.ClubMemberWithdrawResponse;
import org.badminton.api.interfaces.clubmember.dto.RejectApplyResponse;
import org.badminton.domain.domain.clubmember.command.ClubMemberBanCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberExpelCommand;
import org.badminton.domain.domain.clubmember.command.ClubMemberRoleUpdateCommand;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ApplyClubInfo;
import org.badminton.domain.domain.clubmember.info.ApproveApplyInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberBanRecordInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;
import org.badminton.domain.domain.clubmember.info.ClubMemberWithdrawInfo;
import org.badminton.domain.domain.clubmember.info.RejectApplyInfo;
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

	private final ClubMemberFacade clubMemberFacade;
	private final ClubMemberDtoMapper clubMemberDtoMapper;

	@Operation(summary = "동호회 회원 전체 조회",
		description = "동호회에 가입한 회원들의 리스트를 조회합니다.",
		tags = {"ClubMember"})
	@GetMapping
	public CommonResponse<Map<ClubMember.ClubMemberRole, List<ClubMemberResponse>>> getClubMembersInClub(
		@PathVariable String clubToken
	) {
		Map<ClubMember.ClubMemberRole, List<ClubMemberInfo>> clubMemberInfoMap = clubMemberFacade.findAllClubMembers(
			clubToken);
		Map<ClubMember.ClubMemberRole, List<ClubMemberResponse>> clubMemberRoleListMap = clubMemberDtoMapper.of(
			clubMemberInfoMap);

		return CommonResponse.success(clubMemberRoleListMap);
	}

	@Operation(summary = "동호회 가입 신청",
		description = "동호회에 가입을 신청합니다.",
		tags = {"ClubMember"})
	@PostMapping
	public CommonResponse<ApplyClubResponse> applyClub(@AuthenticationPrincipal CustomOAuth2Member member,
		@PathVariable String clubToken) {
		String memberToken = member.getMemberToken();
		ApplyClubInfo applyClubInfo = clubMemberFacade.applyClub(memberToken, clubToken);
		ApplyClubResponse applyClubResponse = clubMemberDtoMapper.of(applyClubInfo);
		return CommonResponse.success(applyClubResponse);
	}

	@Operation(summary = "동호회 가입 승인",
		description = "동호회에 가입을 승인합니다.",
		tags = {"ClubMember"})
	@PostMapping("/approve")
	public CommonResponse<ApproveApplyResponse> approvedClub(@RequestParam Long clubApplyId,
		@PathVariable String clubToken) {
		ApproveApplyInfo approveApplyInfo = clubMemberFacade.approveApplying(clubApplyId);
		ApproveApplyResponse approveApplyResponse = clubMemberDtoMapper.of(approveApplyInfo);

		return CommonResponse.success(approveApplyResponse);
	}

	@Operation(summary = "동호회 가입 거부",
		description = "동호회에 가입을 거부합니다.",
		tags = {"ClubMember"})
	@PostMapping("/reject")
	public CommonResponse<RejectApplyResponse> rejectClub(@RequestParam Long clubApplyId,
		@PathVariable String clubToken) {
		RejectApplyInfo rejectApplyInfo = clubMemberFacade.rejectApplying(clubApplyId);
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
			주의사항:\s
			- ROLE_MANAGER(동호회 관리자)를 강제 탈퇴시키려면 ROLE_OWNER 권한이 필요합니다.""",
		tags = {"ClubMember"}
	)
	@PatchMapping("/role")
	public CommonResponse<ClubMemberResponse> updateClubMemberRole(
		@Valid @RequestBody ClubMemberRoleUpdateRequest request,
		@RequestParam Long clubMemberId, @PathVariable String clubToken) {

		ClubMemberRoleUpdateCommand clubMemberRoleUpdateCommand = request.of();
		ClubMemberInfo clubMemberInfo = clubMemberFacade.updateClubMemberRole(clubMemberRoleUpdateCommand,
			clubMemberId);
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
			
			""",
		tags = {"ClubMember"}
	)
	@PatchMapping("/expel")
	public CommonResponse<ClubMemberBanRecordResponse> expelClubMember(@RequestParam Long clubMemberId,
		@PathVariable String clubToken, @Valid @RequestBody
	ClubMemberExpelRequest request) {
		ClubMemberExpelCommand clubMemberExpelCommand = request.of();

		ClubMemberBanRecordInfo clubMemberBanRecordInfo = clubMemberFacade.expelClubMember(clubMemberExpelCommand,
			clubMemberId);
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
		@Valid @RequestBody ClubMemberBanRequest request) {

		ClubMemberBanCommand clubMemberBanCommand = request.of();
		ClubMemberBanRecordInfo clubMemberBanRecordInfo = clubMemberFacade.banClubMember(clubMemberBanCommand,
			clubMemberId);
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
		ClubMemberWithdrawInfo clubMemberWithdrawInfo = clubMemberFacade.withDrawClubMember(
			clubMemberInfo.clubMemberId());
		ClubMemberWithdrawResponse clubMemberWithdrawResponse = clubMemberDtoMapper.of(clubMemberWithdrawInfo);

		return CommonResponse.success(clubMemberWithdrawResponse);
	}

}

