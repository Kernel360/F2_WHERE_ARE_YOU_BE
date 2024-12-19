package org.badminton.api.interfaces.league.controller;

import java.util.List;

import org.badminton.api.application.league.LeagueFacade;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.league.dto.IsLeagueParticipantResponse;
import org.badminton.api.interfaces.league.dto.LeagueByDateResponse;
import org.badminton.api.interfaces.league.dto.LeagueCancelResponse;
import org.badminton.api.interfaces.league.dto.LeagueCreateRequest;
import org.badminton.api.interfaces.league.dto.LeagueCreateResponse;
import org.badminton.api.interfaces.league.dto.LeagueDetailsResponse;
import org.badminton.api.interfaces.league.dto.LeagueReadResponse;
import org.badminton.api.interfaces.league.dto.LeagueRecruitingCompleteResponse;
import org.badminton.api.interfaces.league.dto.LeagueUpdateRequest;
import org.badminton.api.interfaces.league.dto.LeagueUpdateResponse;
import org.badminton.domain.domain.league.info.IsLeagueParticipantInfo;
import org.badminton.domain.domain.league.info.LeagueByDateInfoWithParticipantCountInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueDetailsInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.badminton.domain.domain.league.info.LeagueRecruitingCompleteInfo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/clubs/{clubToken}/leagues")
public class LeagueController {

	private final LeagueDtoMapper leagueDtoMapper;
	private final LeagueFacade leagueFacade;

	@Operation(
		summary = "월별로 경기 일정을 조회합니다.",
		description = "월별로 경기 일정을 리스트로 조회할 수 있습니다. 날짜는 'yyyy-MM' 형식으로 제공되어야 합니다.",
		tags = {"league"},
		parameters = {
			@Parameter(name = "clubToken", description = "조회할 클럽의 토큰", required = true),
			@Parameter(name = "date", description = "조회할 날짜, 'yyyy-MM' 형식으로 입력", required = true)
		}
	)
	@GetMapping("/month")
	public CommonResponse<List<LeagueReadResponse>> getLeagueByMonth(@PathVariable String clubToken,
		@RequestParam
		@DateTimeFormat(pattern = "yyyy-MM") String date) {
		List<LeagueReadInfo> responseInfo = leagueFacade.getLeaguesByMonth(clubToken, date);
		List<LeagueReadResponse> response = leagueDtoMapper.mapLeagueReadInfoList(responseInfo);
		return CommonResponse.success(response);
	}

	@Operation(
		summary = "일자별로 경기 일정을 조회합니다.",
		description = "일별로 경기 일정을 리스트로 조회할 수 있습니다. 검색 조건으로 날짜를 사용하며, 날짜는 'yyyy-MM' 형식으로 제공되어야 합니다.",
		tags = {"league"},
		parameters = {
			@Parameter(name = "clubToken", description = "조회할 클럽의 토큰", required = true),
			@Parameter(name = "date", description = "조회할 날짜, 'yyyy-MM-dd' 형식으로 입력", required = true)
		}
	)
	@GetMapping("/date")
	public CommonResponse<List<LeagueByDateResponse>> getLeagueByDate(@PathVariable String clubToken,
		@RequestParam
		@DateTimeFormat(pattern = "yyyy-MM-dd") String date) {
		List<LeagueByDateInfoWithParticipantCountInfo> responseInfo = leagueFacade.getLeaguesByDate(clubToken, date);
		List<LeagueByDateResponse> response = leagueDtoMapper.of(responseInfo);
		return CommonResponse.success(response);
	}

	@Operation(
		summary = "경기를 생성합니다.",
		description = """
			경기 생성하고를 데이터베이스에 저장합니다.
			
			1. 경기 이름 2 ~ 20 글자
			2. 경기 설명 2 ~ 1000 글자
			3. 경기 장소 2 ~ 100 글자
			4. 경기 시간: 현재 시간 보다 뒤에 설정
			5. 모집 마감 날짜: 현재 시간 보다 뒤에, 경기 시간 날짜 보다 앞에
			6. 참가인원:
				토너먼트 싱글: 2의 제곱
				토너먼트 더블: 참가자 수/2 가 2의 제곱
				프리 싱글: 2의 배수
				프리 더블: 4의 배수
			
			""",
		tags = {"league"}
	)
	@PostMapping
	public CommonResponse<LeagueCreateResponse> createLeague(
		@PathVariable String clubToken,
		@Valid @RequestBody LeagueCreateRequest leagueCreateRequest,
		@AuthenticationPrincipal CustomOAuth2Member member
	) {
		leagueCreateRequest.validate();
		var command = leagueDtoMapper.of(leagueCreateRequest, clubToken);
		var responseInfo = leagueFacade.createLeague(member.getMemberToken(), clubToken, command);
		LeagueCreateResponse response = leagueDtoMapper.of(responseInfo);
		return CommonResponse.success(response);
	}

	@Operation(
		summary = "특정 경기를 조회합니다.",
		description = "특정 경기를 경기 아이디를 통해 데이터베이스에서 조회합니다.",
		tags = {"league"}
	)
	@GetMapping("/{leagueId}")
	public CommonResponse<LeagueDetailsResponse> leagueRead(@PathVariable String clubToken,
		@PathVariable Long leagueId) {
		LeagueDetailsInfo leagueDetailsInfo =
			leagueFacade.getLeague(clubToken, leagueId);
		return CommonResponse.success(LeagueDetailsResponse.from(leagueDetailsInfo));
	}

	@Operation(
		summary = "경기의 참여자인지 확인합니다",
		description = "경기 아이디를 통해 경기 참가자인지 확인합니다.",
		tags = {"league"}
	)
	@GetMapping("/{leagueId}/check")
	public CommonResponse<IsLeagueParticipantResponse> checkLeagueParticipant(@PathVariable String clubToken,
		@PathVariable Long leagueId,
		@AuthenticationPrincipal CustomOAuth2Member member) {
		IsLeagueParticipantInfo isLeagueParticipantInfo = leagueFacade.isLeagueParticipant(member.getMemberToken(),
			leagueId);

		IsLeagueParticipantResponse isLeagueParticipantResponse = leagueDtoMapper.of(isLeagueParticipantInfo);
		return CommonResponse.success(isLeagueParticipantResponse);
	}

	@Operation(
		summary = "경기의 세부 정보를 변경합니다.",
		description = """
			경기 이름, 설명, 참가자, 싱글/더블, 프리/토너먼트 변경
			
			1. 경기 이름 2 ~ 20 글자
			2. 경기 설명 2 ~ 1000 글자
			3. 참가인원:
				토너먼트 싱글: 2의 제곱
				토너먼트 더블: 참가자 수/2 가 2의 제곱
				프리 싱글: 2의 배수
				프리 더블: 4의 배수
			
			""",
		tags = {"league"}
	)
	@PutMapping("/{leagueId}")
	public CommonResponse<LeagueUpdateResponse> updateLeague(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		@Valid @RequestBody LeagueUpdateRequest leagueUpdateRequest,
		@AuthenticationPrincipal CustomOAuth2Member member
	) {
		leagueUpdateRequest.validate();
		var command = leagueDtoMapper.of(leagueUpdateRequest);
		var leagueUpdateInfo = leagueFacade.updateLeague(clubToken, leagueId, command, member.getMemberToken());
		LeagueUpdateResponse leagueUpdateResponse = leagueDtoMapper.of(leagueUpdateInfo);
		return CommonResponse.success(leagueUpdateResponse);
	}

	@Operation(
		summary = "경기 취소",
		description = "경기를 취소합니다.",
		tags = {"league"}
	)
	@DeleteMapping("/{leagueId}")
	public CommonResponse<LeagueCancelResponse> cancelLeague(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		@AuthenticationPrincipal CustomOAuth2Member member) {
		LeagueCancelInfo leagueInfo = leagueFacade.cancelLeague(clubToken, leagueId, member.getMemberToken());
		LeagueCancelResponse leagueCancelResponse = leagueDtoMapper.of(leagueInfo);
		return CommonResponse.success(leagueCancelResponse);
	}

	@Operation(
		summary = "경기 모집 마감",
		description = "경기 모집 마감 기한 전이라도, 경기 Owner가 모집을 마감할 수 있습니다. 이때 단식(2)/복식(4)에 따른 최소 인원 조건은 충족해야 합니다.",
		tags = {"league"}
	)
	@PatchMapping("/{leagueId}")
	public CommonResponse<LeagueRecruitingCompleteResponse> completeLeagueRecruiting(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		@AuthenticationPrincipal CustomOAuth2Member member
	) {
		LeagueRecruitingCompleteInfo leagueRecruitingCompleteInfo = leagueFacade.completeLeagueRecruiting(clubToken,
			leagueId,
			member.getMemberToken());
		LeagueRecruitingCompleteResponse leagueRecruitingCompleteResponse = leagueDtoMapper.of(
			leagueRecruitingCompleteInfo);
		return CommonResponse.success(leagueRecruitingCompleteResponse);
	}
}
