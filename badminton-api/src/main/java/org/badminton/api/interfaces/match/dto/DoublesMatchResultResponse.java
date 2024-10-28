package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.domain.match.info.DoublesMatchResultInfo;

public record DoublesMatchResultResponse(
        TeamResultResponse currentTeam,
        TeamResultResponse opponentTeam,
        MatchResult currentTeamResult,
        MatchResult opponentTeamResult
) {
    public static DoublesMatchResultResponse from(DoublesMatchResultInfo matchResultInfo) {
        return new DoublesMatchResultResponse(
                TeamResultResponse.from(matchResultInfo.currentTeam()),
                TeamResultResponse.from(matchResultInfo.opponentTeam()),
                matchResultInfo.currentTeamResult(),
                matchResultInfo.opponentTeamResult()
        );
    }

}
