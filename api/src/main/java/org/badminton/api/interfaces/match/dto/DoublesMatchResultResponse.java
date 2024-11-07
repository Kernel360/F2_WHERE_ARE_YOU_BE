package org.badminton.api.interfaces.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.domain.match.info.DoublesMatchResultInfo;

public record DoublesMatchResultResponse(
        @Schema(description = "내팀 경기 참가자 1 이름")
        String myTeamParticipant1Name,
        @Schema(description = "내팀 경기 참가자 2 이름")
        String myTeamParticipant2Name,
        @Schema(description = "상대팀 경기 참가자 1 아이디")
        String opponentTeamParticipant1Name,
        @Schema(description = "상대팀 경기 참가자 2 아이디")
        String opponentTeamParticipant2Name,
        @Schema(description = "내 팀 매치 결과")
        MatchResult currentTeamResult,
        @Schema(description = "상대팀 매치 결과")
        MatchResult opponentTeamResult
) {
    public static DoublesMatchResultResponse from(DoublesMatchResultInfo matchResultInfo) {
        return new DoublesMatchResultResponse(
                matchResultInfo.currentTeam().participant1Name(),
                matchResultInfo.currentTeam().participant2Name(),
                matchResultInfo.opponentTeam().participant1Name(),
                matchResultInfo.opponentTeam().participant2Name(),
                matchResultInfo.currentTeamResult(),
                matchResultInfo.opponentTeamResult()
        );
    }

}
