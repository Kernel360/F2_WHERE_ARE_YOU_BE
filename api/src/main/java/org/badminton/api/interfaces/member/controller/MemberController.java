package org.badminton.api.interfaces.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.badminton.api.application.match.MyMatchFacade;
import org.badminton.api.application.member.MemberFacade;
import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.badminton.api.aws.s3.service.MemberProfileImageService;
import org.badminton.api.common.exception.member.ImageFileNotFoundException;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.match.dto.MatchResultResponse;
import org.badminton.api.interfaces.member.MemberDtoMapper;
import org.badminton.api.interfaces.member.dto.MemberIsClubMemberResponse;
import org.badminton.api.interfaces.member.dto.MemberMyPageResponse;
import org.badminton.api.interfaces.member.dto.MemberUpdateRequest;
import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;
import org.badminton.domain.domain.match.info.MatchResultInfo;
import org.badminton.domain.domain.member.info.MemberIsClubMemberInfo;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/members")
@Slf4j
public class MemberController {

    private final MemberProfileImageService memberProfileImageService;
    private final MyMatchFacade myMatchFacade;
    private final MemberFacade memberFacade;
    private final MemberDtoMapper memberDtoMapper;

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
        MemberMyPageResponse memberMyPageResponse = memberDtoMapper.of(memberMyPageInfo);
        return CommonResponse.success(memberMyPageResponse);
    }

    @Operation(
            summary = "회원이 동호회에 가입되어있는지 확인합니다",
            description = "회원이 동호회에 가입되어있는지 확인합니다",
            tags = {"Member"}
    )
    @GetMapping("/is-club-member")
    public CommonResponse<MemberIsClubMemberResponse> getMemberIsClubMember(
            @AuthenticationPrincipal CustomOAuth2Member member) {
        String memberToken = member.getMemberToken();
        MemberIsClubMemberInfo memberIsClubMemberInfo = memberFacade.getMemberIsClubMember(memberToken);
        MemberIsClubMemberResponse memberIsClubMemberResponse = memberDtoMapper.of(memberIsClubMemberInfo);
        return CommonResponse.success(memberIsClubMemberResponse);
    }

    @GetMapping("/matchesRecord")
    @Operation(summary = "동호회 회원 경기 조회",
            description = "동호회 회원 경기 조회.",
            tags = {"Member"})
    public CommonResponse<List<MatchResultResponse>> readMemberLeagueRecord(
            @AuthenticationPrincipal CustomOAuth2Member member
    ) {
        ClubMemberMyPageInfo clubMemberMyPageInfo = memberFacade.getClubMember(member.getMemberToken());

        if (clubMemberMyPageInfo == null) {
            return CommonResponse.success(null);
        }
        Long clubMemberId = clubMemberMyPageInfo.clubMemberId();
        List<MatchResultInfo> myMatch = myMatchFacade.getMyMatch(clubMemberId);
        List<MatchResultResponse> matchResultResponseList = myMatch.stream()
                .map(MatchResultResponse::from)
                .toList();
        return CommonResponse.success(matchResultResponseList);
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
}

