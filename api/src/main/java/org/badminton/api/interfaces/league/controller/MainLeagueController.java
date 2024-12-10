package org.badminton.api.interfaces.league.controller;

import java.time.LocalDate;
import java.util.List;

import org.badminton.api.application.league.LeagueFacade;
import org.badminton.api.application.match.MatchFacade;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.club.dto.CustomPageResponse;
import org.badminton.api.interfaces.league.dto.OngoingAndUpcomingLeagueResponse;
import org.badminton.api.interfaces.match.dto.LeagueSetsScoreInProgressResponse;
import org.badminton.api.interfaces.match.dto.SetScoreResponse;
import org.badminton.domain.domain.league.enums.AllowedLeagueStatus;
import org.badminton.domain.domain.league.enums.Region;
import org.badminton.domain.domain.league.info.LeagueReadPageInfo;
import org.badminton.domain.domain.league.info.OngoingAndUpcomingLeagueInfo;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/leagues")
public class MainLeagueController {

	private static final String DEFAULT_PAGE_VALUE = "0";
	private static final String DEFAULT_SIZE_VALUE = "9";
	private final MatchFacade matchFacade;
	private final LeagueFacade leagueFacade;

	@Operation(
		summary = "메인페이지에서 일별로 진행 중, 또는 진행 예정인 경기 일정을 조회한다.",
		description = "메인페이지에서 일별로 진행 중 또는 진행 예정인 경기 일정을 조회합니다. 날짜는 'yyyy-MM-dd' 형식으로 제공되어야 합니다.",
		tags = {"main-league"}
	)
	@GetMapping
	public CommonResponse<CustomPageResponse<OngoingAndUpcomingLeagueResponse>> getLeaguesByDate(
		@RequestParam(defaultValue = "ALL") AllowedLeagueStatus leagueStatus,
		@RequestParam(defaultValue = "ALL") Region region,
		@RequestParam("date")
		@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
		@RequestParam(defaultValue = DEFAULT_PAGE_VALUE) int page,
		@RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size
	) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "leagueAt"));
		Page<OngoingAndUpcomingLeagueInfo> leaguePage = leagueFacade.getOngoingAndUpcomingLeaguesByDate(
			leagueStatus, region, date, pageable);

		Page<OngoingAndUpcomingLeagueResponse> response = leaguePage.map(OngoingAndUpcomingLeagueResponse::from);

		leaguePage.getContent().stream()
			.map(OngoingAndUpcomingLeagueResponse::from)
			.toList();

		CustomPageResponse<OngoingAndUpcomingLeagueResponse> pageResponse = new CustomPageResponse<>(response);
		return CommonResponse.success(pageResponse);

	}

	@Operation(
		summary = "메인페이지의 각각의 경기를 눌렀을 때, 경기가 진행 중일 경우 점수를 조회한다.",
		tags = {"main-league"}
	)
	@GetMapping("/{leagueId}")
	public CommonResponse<List<LeagueSetsScoreInProgressResponse>> getLeagueScores(@PathVariable Long leagueId) {
		List<LeagueSetsScoreInProgressInfo> leagueSetsScoreInProgressInfos = matchFacade.retrieveLeagueMatchSetsScoreInProgress(
			leagueId);
		return CommonResponse.success(leagueSetsScoreInProgressInfos.stream()
			.map(LeagueSetsScoreInProgressResponse::from)
			.toList());
	}

	@Operation(
		summary = "메인페이지에서 진행 중인 경기의 세트 점수를 조회한다.",
		description = "실시간성 보장을 위해 폴링 방식으로 요청하는 API 입니다.",
		tags = {"main-league"}
	)
	@GetMapping("/{leagueId}/matches/{matchId}/sets/{setNumber}")
	public CommonResponse<SetScoreResponse> getMatchSetScores(
		@PathVariable Long leagueId,
		@PathVariable Long matchId,
		@PathVariable Integer setNumber
	) {
		SetInfo.Main setInfo = matchFacade.retrieveSetInfo(leagueId, matchId, setNumber);
		return CommonResponse.success(SetScoreResponse.fromSetInfo(setInfo));
	}

	@Operation(
		summary = "경기 전체에 대한 정보를 가져옵니다.",
		description = "경기 전체에 대한 정보를 페이지네이션으로 가져옵니다.",
		tags = {"main-league"}
	)
	@GetMapping("/all")
	public CommonResponse<List<LeagueReadPageInfo>> getAllLeague(
		@RequestParam(defaultValue = DEFAULT_PAGE_VALUE) int page,
		@RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size
	) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "leagueAt"));
		return CommonResponse.success(leagueFacade.getLeaguePageList(pageable));
	}
}
