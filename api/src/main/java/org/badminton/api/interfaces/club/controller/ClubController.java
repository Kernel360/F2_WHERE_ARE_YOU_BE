package org.badminton.api.interfaces.club.controller;

import java.util.List;

import org.badminton.api.application.club.ClubFacade;
import org.badminton.api.application.club.ClubRankFacade;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.club.dto.ClubApplicantResponse;
import org.badminton.api.interfaces.club.dto.ClubCardResponse;
import org.badminton.api.interfaces.club.dto.ClubCreateRequest;
import org.badminton.api.interfaces.club.dto.ClubCreateResponse;
import org.badminton.api.interfaces.club.dto.ClubDeleteResponse;
import org.badminton.api.interfaces.club.dto.ClubDetailsResponse;
import org.badminton.api.interfaces.club.dto.ClubUpdateRequest;
import org.badminton.api.interfaces.club.dto.ClubUpdateResponse;
import org.badminton.api.interfaces.club.dto.CustomPageResponse;
import org.badminton.domain.common.policy.ClubMemberPolicy;
import org.badminton.domain.domain.club.command.ClubCreateCommand;
import org.badminton.domain.domain.club.command.ClubUpdateCommand;
import org.badminton.domain.domain.club.info.ClubApplicantInfo;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.club.info.ClubDeleteInfo;
import org.badminton.domain.domain.club.info.ClubUpdateInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/clubs")
public class ClubController {

	private static final String DEFAULT_PAGE_VALUE = "0";
	private static final String DEFAULT_SIZE_VALUE = "9";
	private static final String DEFAULT_SORT_BY_VALUE = "clubId";
	private final ClubFacade clubFacade;
	private final ClubRankFacade clubRankFacade;
	private final ClubDtoMapper clubDtoMapper;
	private final ClubMemberPolicy clubMemberPolicy;

	@GetMapping("/{clubToken}")
	@Operation(summary = "동호회 조회",
		description = "동호회를 조회합니다.",
		tags = {"Club"})
	public CommonResponse<ClubDetailsResponse> readClub(@PathVariable String clubToken) {
		var result = clubFacade.readClub(clubToken);
		var response = clubDtoMapper.of(result);
		return CommonResponse.success(response);
	}

	@PatchMapping("{clubToken}")
	@Operation(summary = "동호회 수정",
		description = """
			새로운 동호회를 수정합니다. 다음 조건을 만족해야 합니다:
			
			1. 동호회 이름:
			   - 필수 입력
			   - 2자 이상 20자 이하
			
			2. 동호회 소개:
			   - 2자 이상 1000자 이하
			
			3. 동호회 이미지 URL:
			   - 호스트: badminton-team.s3.ap-northeast-2.amazonaws.com
			   - 경로: /club-banner/로 시작
			   - 파일 확장자: png, jpg, jpeg, gif 중 하나""",
		tags = {"Club"}
	)
	public CommonResponse<ClubUpdateResponse> updateClub(@PathVariable String clubToken,
		@Valid @RequestBody ClubUpdateRequest clubUpdateRequest, @AuthenticationPrincipal CustomOAuth2Member member) {

		clubMemberPolicy.validateClubMember(member.getMemberToken(), clubToken);
		ClubUpdateCommand command = clubDtoMapper.of(clubUpdateRequest);
		ClubUpdateInfo clubUpdateInfo = clubFacade.updateClubInfo(command, clubToken, member.getMemberToken());
		ClubUpdateResponse response = clubDtoMapper.of(clubUpdateInfo);
		return CommonResponse.success(response);
	}

	@PostMapping
	@Operation(summary = "동호회 추가",
		description = """
			새로운 동호회를 생성합니다. 다음 조건을 만족해야 합니다:
			1. 동호회 이름:
			   - 필수 입력
			   - 2자 이상 20자 이하
			
			2. 동호회 소개:
			   - 2자 이상 1000자 이하
			
			3. 동호회 이미지 URL:
			   - 호스트: badminton-team.s3.ap-northeast-2.amazonaws.com
			   - 경로: /club-banner/로 시작
			   - 파일 확장자: png, jpg, jpeg, gif 중 하나
			   - https://d36om9pjoifd2y.cloudfront.net/club-banner/804a0dfc-947f-4039-acbe-d95a85893087.png
			""", tags = {"Club"}
	)
	public CommonResponse<ClubCreateResponse> createClub(@Valid @RequestBody ClubCreateRequest clubCreateRequest,
		@AuthenticationPrincipal CustomOAuth2Member member) {
		String memberToken = member.getMemberToken();
		ClubCreateCommand clubCreateCommand = clubDtoMapper.of(clubCreateRequest);
		ClubCreateInfo created = clubFacade.createClub(clubCreateCommand, memberToken);
		ClubCreateResponse response = clubDtoMapper.of(created);
		return CommonResponse.success(response);
	}

	@DeleteMapping("{clubToken}")
	@Operation(summary = "동호회 삭제",
		description = "동호회를 삭제합니다.",
		tags = {"Club"})
	public CommonResponse<ClubDeleteResponse> deleteClub(@PathVariable String clubToken,
		@AuthenticationPrincipal CustomOAuth2Member member) {
		ClubDeleteInfo club = clubFacade.deleteClubInfo(member.getMemberToken(), clubToken);
		ClubDeleteResponse deleted = clubDtoMapper.of(club);
		return CommonResponse.success(deleted);
	}

	@GetMapping
	@Operation(summary = "전체 동호회 조회",
		description = "전체 동호회를 조회합니다.",
		tags = {"Club"})
	public CommonResponse<CustomPageResponse<ClubCardResponse>> readAllClub(
		@RequestParam(defaultValue = DEFAULT_PAGE_VALUE) int page,
		@RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size,
		@RequestParam(defaultValue = DEFAULT_SORT_BY_VALUE) String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		Page<ClubCardInfo> clubCard = clubFacade.readAllClubs(pageable);
		Page<ClubCardResponse> response = clubDtoMapper.of(clubCard);
		CustomPageResponse<ClubCardResponse> pageResponse = new CustomPageResponse<>(response);
		return CommonResponse.success(pageResponse);
	}

	@Operation(summary = "검색 조건에 맞는 동호회 조회",
		description = "검색 조건에 맞는 동호회를 조회합니다.",
		tags = {"Club"})
	@GetMapping("/search")
	public CommonResponse<CustomPageResponse<ClubCardResponse>> clubSearch(
		@RequestParam(defaultValue = DEFAULT_PAGE_VALUE) int page,
		@RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size,
		@RequestParam(defaultValue = DEFAULT_SORT_BY_VALUE) String sort,
		@RequestParam(required = false) String keyword) {

		var clubCard = clubFacade.searchClubs(keyword, page, size, sort);
		var response = clubDtoMapper.of(clubCard);
		var pageResponse = new CustomPageResponse<>(response);
		return CommonResponse.success(pageResponse);
	}

	@Operation(summary = "인기 top10 동호회 검색",
		description = "인기 top10 동호회를 검색합니다.",
		tags = {"Club"})
	@GetMapping("/popular")
	public CommonResponse<List<ClubCardResponse>> clubSearchPopular() {
		var clubCardList = clubRankFacade.getTop10PopularClub();
		return CommonResponse.success(clubDtoMapper.of(clubCardList));
	}

	@Operation(summary = "최근 활동이 많은 top10 동호회 검색",
		description = "최근 활동이 많은 동호회 top10을 검색합니다.",
		tags = {"Club"})
	@GetMapping("/activity")
	public CommonResponse<List<ClubCardResponse>> clubSearchActivity() {
		var clubCardList = clubRankFacade.getTop10RecentlyActiveClub();
		return CommonResponse.success(clubDtoMapper.of(clubCardList));
	}

	@Operation(summary = "최근 생성된 top10 동호회 검색",
		description = "최근 생성된 동호회 top10을 검색합니다.",
		tags = {"Club"})
	@GetMapping("/recently")
	public CommonResponse<List<ClubCardResponse>> clubSearchRecently() {
		var clubCardList = clubRankFacade.getTop10RecentlyCreatedClub();
		return CommonResponse.success(clubDtoMapper.of(clubCardList));
	}

	@Operation(summary = "특정 동호회에 가입 신청한 유저 리스트 조회",
		description = "특정 동호회에 가입 신청한 유저 리스트 조회",
		tags = {"Club"})
	@GetMapping("/{clubToken}/applicants")
	public CommonResponse<CustomPageResponse<ClubApplicantResponse>> getClubApplicant(
		@PathVariable String clubToken,
		@AuthenticationPrincipal CustomOAuth2Member member,
		@RequestParam(defaultValue = DEFAULT_PAGE_VALUE) int page,
		@RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size
	) {
		Sort sort = Sort.by(Sort.Order.by("createdAt"));
		Page<ClubApplicantInfo> clubApplies = clubFacade.readClubApplicants(member.getMemberToken(), clubToken,
			PageRequest.of(page, size, sort));
		Page<ClubApplicantResponse> clubApply = clubApplies.map(ClubApplicantResponse::from);
		return CommonResponse.success(new CustomPageResponse<>(clubApply));
	}
}
