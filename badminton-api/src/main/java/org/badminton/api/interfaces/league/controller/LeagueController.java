package org.badminton.api.interfaces.league.controller;

import java.util.List;

import org.badminton.api.application.league.LeagueFacade;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.league.dto.LeagueByDateResponse;
import org.badminton.api.interfaces.league.dto.LeagueCancelResponse;
import org.badminton.api.interfaces.league.dto.LeagueCreateRequest;
import org.badminton.api.interfaces.league.dto.LeagueCreateResponse;
import org.badminton.api.interfaces.league.dto.LeagueDetailsResponse;
import org.badminton.api.interfaces.league.dto.LeagueReadResponse;
import org.badminton.api.interfaces.league.dto.LeagueUpdateRequest;
import org.badminton.api.interfaces.league.dto.LeagueUpdateResponse;
import org.badminton.api.interfaces.oauth.dto.CustomOAuth2Member;
import org.badminton.domain.domain.league.info.LeagueByDateInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueDetailsInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.springframework.format.annotation.DateTimeFormat;
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
			@Parameter(name = "clubId", description = "조회할 클럽의 ID", required = true),
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
			@Parameter(name = "clubId", description = "조회할 클럽의 ID", required = true),
			@Parameter(name = "date", description = "조회할 날짜, 'yyyy-MM-dd' 형식으로 입력", required = true)
		}
	)
	@GetMapping("/date")
	public CommonResponse<List<LeagueByDateResponse>> getLeagueByDate(@PathVariable String clubToken,
		@RequestParam
		@DateTimeFormat(pattern = "yyyy-MM-dd") String date) {
		List<LeagueByDateInfo> responseInfo = leagueFacade.getLeaguesByDate(clubToken, date);
		List<LeagueByDateResponse> response = leagueDtoMapper.mapLeagueByDateInfoList(responseInfo);
		return CommonResponse.success(response);
	}

	@Operation(
		summary = "경기를 생성합니다.",
		description = "경기 생성하고를 데이터베이스에 저장합니다.",
		tags = {"league"}
	)
	@PostMapping
	public CommonResponse<LeagueCreateResponse> createLeague(
		@PathVariable String clubToken,
		@Valid @RequestBody LeagueCreateRequest leagueCreateRequest) {
		var command = leagueDtoMapper.of(leagueCreateRequest, clubToken);
		var responseInfo = leagueFacade.createLeague(clubToken, command);
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
		@PathVariable Long leagueId,
		@AuthenticationPrincipal CustomOAuth2Member member) {
		LeagueDetailsInfo leagueDetailsInfo =
			leagueFacade.getLeague(clubToken, leagueId, member.getMemberToken());
		LeagueDetailsResponse leagueDetailsResponse = leagueDtoMapper.of(leagueDetailsInfo);
		return CommonResponse.success(leagueDetailsResponse);
	}

	@Operation(
		summary = "경기의 세부 정보를 변경합니다.",
		description = "경기 제목, 경기 상태 등을 변경할 수 있습니다.",
		tags = {"league"}
	)
	@PatchMapping("/{leagueId}")
	public CommonResponse<LeagueUpdateResponse> updateLeague(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		@Valid @RequestBody LeagueUpdateRequest leagueUpdateRequest) {
		var command = leagueDtoMapper.of(leagueUpdateRequest);
		var leagueUpdateInfo = leagueFacade.updateLeague(clubToken, leagueId, command);
		var leagueUpdateResponse = leagueDtoMapper.of(leagueUpdateInfo);
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
		@PathVariable Long leagueId) {
		LeagueCancelInfo leagueInfo = leagueFacade.cancelLeague(clubToken, leagueId);
		LeagueCancelResponse LeagueCancel = leagueDtoMapper.of(leagueInfo);

		return CommonResponse.success(LeagueCancel);
	}
}
