package org.badminton.api.interfaces.match.controller;

import java.util.List;

import org.badminton.api.application.match.MatchFacade;
import org.badminton.api.application.match.MatchOperationHandler;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.match.dto.BracketResponse;
import org.badminton.api.interfaces.match.dto.MatchDetailsResponse;
import org.badminton.api.interfaces.match.dto.MatchSetResponse;
import org.badminton.api.interfaces.match.dto.SetScoreFinishResponse;
import org.badminton.api.interfaces.match.dto.SetScoreResponse;
import org.badminton.api.interfaces.match.dto.SetScoreUpdateRequest;
import org.badminton.api.interfaces.match.dto.StartMatchCommand;
import org.badminton.domain.common.policy.ClubMemberPolicy;
import org.badminton.domain.domain.match.command.MatchCommand.UpdateSetScore;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clubs/{clubToken}/leagues/{leagueId}/matches")
public class MatchController {

	private final MatchFacade matchFacade;
	private final ClubMemberPolicy clubMemberPolicy;

	@GetMapping
	@Operation(summary = "대진표 조회",
		description = "대진표를 조회합니다.",
		tags = {"Match"})
	public CommonResponse<BracketResponse> getAllMatches(
		@PathVariable String clubToken,
		@PathVariable Long leagueId
	) {
		BracketInfo bracketInfo = matchFacade.retrieveBracket(leagueId);
		return CommonResponse.success(BracketResponse.fromBracketInfo(bracketInfo));
	}

	@GetMapping("/{matchId}")
	@Operation(summary = "특정 게임의 세트별 점수 상세 조회",
		description = "특정 게임의 세트별 점수를 상세 조회합니다. FINISHED 인 세트만 조회합니다.",
		tags = {"Match"})
	public CommonResponse<MatchDetailsResponse> getMatchDetails(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		@PathVariable Long matchId
	) {
		MatchInfo.SetScoreDetails matchDetailsInLeague = matchFacade.retrieveAllSetsScoreInMatch(leagueId, matchId);
		return CommonResponse.success(MatchDetailsResponse.fromMatchDetailsInfo(matchDetailsInLeague));
	}

	@GetMapping("/sets")
	@Operation(summary = "모든 게임의 세트 점수 상세 조회",
		description = "모든 게임의 세트 점수를 상세 조회합니다. 모든 게임의 세트별 점수를 조회할 수 있습니다.",
		tags = {"Match"})
	public CommonResponse<List<SetScoreResponse>> getAllMatchesDetails(
		@PathVariable String clubToken,
		@PathVariable Long leagueId
	) {
		List<SetInfo.Main> allSetsScoreInLeague = matchFacade.retrieveAllSetsScoreInBracket(leagueId);
		List<SetScoreResponse> setScoreResponseList = allSetsScoreInLeague.stream()
			.map(SetScoreResponse::fromSetInfo)
			.toList();
		return CommonResponse.success(setScoreResponseList);
	}

	@PostMapping()
	@Operation(summary = "대진표 생성",
		description = "대진표를 생성합니다.",
		tags = {"Match"})
	public CommonResponse<BracketResponse> generateBracket(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		@AuthenticationPrincipal CustomOAuth2Member member
	) {
		MatchOperationHandler matchOperationFacade = matchFacade.getMatchOperationHandler(leagueId);
		BracketInfo bracketInfo = matchOperationFacade.generateInitialBracket(leagueId, member.getMemberToken());
		return CommonResponse.success(BracketResponse.fromBracketInfo(bracketInfo));
	}

	@GetMapping("/{matchId}/sets/{setNumber}")
	@Operation(summary = "스코어 보드에서 특정 매치의 세트를 조회합니다.",
		description = "스코어 보드에서 세트 점수, 경기 참여자 정보, 이긴 세트 수, 현황 등을 조회할 수 있습니다.",
		tags = {"Match"})
	public CommonResponse<MatchSetResponse> getMatchSet(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		@PathVariable Long matchId,
		@PathVariable Integer setNumber
	) {
		MatchSetInfo matchSetInfo = matchFacade.retrieveMatchSetInfo(leagueId, matchId, setNumber);
		return CommonResponse.success(MatchSetResponse.from(matchSetInfo));
	}

	@PatchMapping("/{matchId}/sets/{setNumber}")
	@Operation(summary = "스코어 보드 점수 저장",
		description = "스코어 보드 점수판을 눌렀을 때 실행되는 API",
		tags = {"Match"})
	public CommonResponse<SetScoreResponse> setScoreBoard(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		@PathVariable Long matchId,
		@PathVariable Integer setNumber,
		@Valid @RequestBody SetScoreUpdateRequest setScoreUpdateRequest,
		@AuthenticationPrincipal CustomOAuth2Member member
	) {
		String memberToken = member.getMemberToken();
		SetInfo.Main setInfo = matchFacade.registerSetScore(leagueId, matchId, setNumber, setScoreUpdateRequest,
			memberToken);
		return CommonResponse.success(SetScoreResponse.fromSetInfo(setInfo));
	}

	@PostMapping("/{matchId}/sets/{setNumber}")
	@Operation(summary = "세트 종료 버튼, 누르면 다음 세트가 시작합니다.",
		description = """
			0~30 사이의 숫자만 입력할 수 있습니다.
			현재 세트 종료 버튼을 누를 때 실행되는 API
			""",
		tags = {"Match"})
	public CommonResponse<SetScoreFinishResponse> finishSetsScore(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		@PathVariable Long matchId,
		@PathVariable Integer setNumber,
		@Valid @RequestBody SetScoreUpdateRequest setScoreRequest,
		@AuthenticationPrincipal CustomOAuth2Member member
	) {
		String memberToken = member.getMemberToken();
		MatchOperationHandler matchOperationFacade = matchFacade.getMatchOperationHandler(leagueId);
		UpdateSetScore updateSetScoreCommand = UpdateSetScore.builder()
			.score1(setScoreRequest.score1())
			.score2(setScoreRequest.score2())
			.build();

		SetInfo.Main updateSetScoreInfo = matchOperationFacade.registerSetScoreInMatch(leagueId, matchId, setNumber,
			updateSetScoreCommand, memberToken);
		return CommonResponse.success(SetScoreFinishResponse.fromUpdateSetScoreInfo(updateSetScoreInfo));
	}

	@Operation(summary = "매치 시작  - 첫 세트가 IN_PROGRESS 가 됨",
		description = """
						
			첫 세트가 IN_PROGRESS 로 변경됩니다.
						
			""",
		tags = {"Match"})
	@PostMapping("/{matchId}/sets/init")
	public CommonResponse<String> startMatch(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		@PathVariable Long matchId,
		@AuthenticationPrincipal CustomOAuth2Member member
	) {
		clubMemberPolicy.validateLeagueParticipant(leagueId, member.getMemberToken());
		StartMatchCommand startMatchCommand = new StartMatchCommand(clubToken, leagueId, matchId);
		MatchOperationHandler matchOperationFacade = matchFacade.getMatchOperationHandler(leagueId);
		matchOperationFacade.startMatch(startMatchCommand);
		return CommonResponse.success("OK");
	}
}
