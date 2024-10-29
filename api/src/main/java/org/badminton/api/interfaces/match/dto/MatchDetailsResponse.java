package org.badminton.api.interfaces.match.dto;

import java.util.List;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.info.MatchInfo;

public record MatchDetailsResponse(
        Long matchId,
        Long leagueId,
        MatchType matchType,
        SinglesMatchResponse singlesMatch,
        DoublesMatchResponse doublesMatch,
        List<SinglesSetResponse> singlesSets,
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
