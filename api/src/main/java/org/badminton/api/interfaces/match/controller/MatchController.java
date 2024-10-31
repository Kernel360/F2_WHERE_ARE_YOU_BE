package org.badminton.api.interfaces.match.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.badminton.api.application.match.MatchFacade;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.match.dto.BracketResponse;
import org.badminton.api.interfaces.match.dto.MatchDetailsResponse;
import org.badminton.api.interfaces.match.dto.MatchSetResponse;
import org.badminton.api.interfaces.match.dto.SetScoreResponse;
import org.badminton.api.interfaces.match.dto.SetScoreUpdateRequest;
import org.badminton.api.interfaces.match.dto.SetScoreUpdateResponse;
import org.badminton.domain.domain.match.command.MatchCommand.UpdateSetScore;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clubs/{clubToken}/leagues/{leagueId}/matches")
public class MatchController {

    private final MatchFacade matchFacade;

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
            description = "특정 게임의 세트별 점수를 상세 조회합니다.",
            tags = {"Match"})
    public CommonResponse<MatchDetailsResponse> getMatchDetails(
            @PathVariable String clubToken,
            @PathVariable Long leagueId,
            @PathVariable Long matchId
    ) {
        MatchInfo.SetScoreDetails matchDetailsInLeague = matchFacade.retrieveAllSetsScoreInMatch(leagueId, matchId);
        return CommonResponse.success(MatchDetailsResponse.fromMatchDetailsInfo(matchDetailsInLeague));
    }

    @PostMapping
    @Operation(summary = "대진표 생성",
            description = "대진표를 생성합니다.",
            tags = {"Match"})
    public CommonResponse<BracketResponse> makeMatches(
            @PathVariable String clubToken,
            @PathVariable Long leagueId
    ) {
        BracketInfo bracketInfo = matchFacade.generateInitialBracket(leagueId);
        return CommonResponse.success(BracketResponse.fromBracketInfo(bracketInfo));
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

    @GetMapping("/{matchId}/sets/{setNumber}")
    @Operation(summary = "스코어 보드에서 특정 매치의 세트를 조회합니다.",
            description = "",
            tags = {"Match"})
    public CommonResponse<MatchSetResponse> getMatchSet(
            @PathVariable String clubToken,
            @PathVariable Long leagueId,
            @PathVariable Long matchId,
            @PathVariable int setNumber
    ) {
        MatchSetInfo matchSetInfo = matchFacade.retrieveMatchSetInfo(matchId, setNumber);
        return CommonResponse.success(MatchSetResponse.from(matchSetInfo));
    }

    @PostMapping("/{matchId}/sets/{setNumber}")
    @Operation(summary = "세트별 점수 저장",
            tags = {"Match"})
    public CommonResponse<SetScoreUpdateResponse> updateSetsScore(
            @PathVariable String clubToken,
            @PathVariable Long leagueId,
            @PathVariable Long matchId,
            @PathVariable int setNumber,
            @Valid @RequestBody SetScoreUpdateRequest setScoreUpdateRequest
    ) {
        UpdateSetScore updateSetScoreCommand = UpdateSetScore.builder()
                .score1(setScoreUpdateRequest.score1())
                .score2(setScoreUpdateRequest.score2())
                .build();

        SetInfo.Main updateSetScoreInfo = matchFacade.registerSetScoreInMatch(leagueId, matchId,
                setNumber, updateSetScoreCommand);
        return CommonResponse.success(SetScoreUpdateResponse.fromUpdateSetScoreInfo(updateSetScoreInfo));
    }
}
