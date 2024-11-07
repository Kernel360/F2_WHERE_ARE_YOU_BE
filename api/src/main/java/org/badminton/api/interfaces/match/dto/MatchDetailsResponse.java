package org.badminton.api.interfaces.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.info.MatchInfo;

public record MatchDetailsResponse(
        @Schema(description = "매치 아이디", requiredMode = RequiredMode.REQUIRED)
        Long matchId,
        @Schema(description = "경기 아이디", requiredMode = RequiredMode.REQUIRED)
        Long leagueId,
        @Schema(description = "매치 타입", requiredMode = RequiredMode.REQUIRED)
        MatchType matchType,
        @Schema(description = "단식 경기")
        SinglesMatchResponse singlesMatch,
        @Schema(description = "복식 경기")
        DoublesMatchResponse doublesMatch,
        @Schema(description = "단식 경기 세트")
        List<SinglesSetResponse> singlesSets,
        @Schema(description = "복식 경기 세트")
        List<DoublesSetResponse> doublesSets
) {

    public static MatchDetailsResponse fromMatchDetailsInfo(MatchInfo.SetScoreDetails matchDetails) {
        return new MatchDetailsResponse(
                matchDetails.matchId(),
                matchDetails.leagueId(),
                matchDetails.matchType(),
                matchDetails.matchType() == MatchType.SINGLES
                        ? SinglesMatchResponse.fromSinglesMatchInfo(matchDetails.singlesMatch())
                        : null,
                matchDetails.matchType() == MatchType.DOUBLES
                        ? DoublesMatchResponse.fromDoublesMatchInfo(matchDetails.doublesMatch())
                        : null,
                matchDetails.matchType() == MatchType.SINGLES
                        ? SinglesSetResponse.fromSinglesSetInfoList(matchDetails.singlesSets())
                        : null,
                matchDetails.matchType() == MatchType.DOUBLES
                        ? DoublesSetResponse.fromDoublesSetInfoList(matchDetails.doublesSets())
                        : null
        );
    }
}
