package org.badminton.api.interfaces.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;
import org.badminton.domain.common.enums.BracketType;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.info.BracketInfo;

public record BracketResponse(
        @Schema(description = "경기 아이디", requiredMode = RequiredMode.REQUIRED)
        Long leagueId,
        @Schema(description = "대진표 생성 타입(FREE | TOURNAMENT)", requiredMode = RequiredMode.REQUIRED)
        BracketType bracketType,
        @Schema(description = "매치 타입(SINGLES | DOUBLES)", requiredMode = RequiredMode.REQUIRED)
        MatchType matchType,
        @Schema(description = "매치 상태(NOT_STARTED | IN_PROGRESS | FINISHED)", requiredMode = RequiredMode.REQUIRED)
        MatchStatus matchStatus,
        @Schema(description = "라운드 번호(단식이면 1, 2, 3, ... | 복식이면 ... 32, 16, 8, 4, 2, 1", requiredMode = RequiredMode.REQUIRED)
        int roundNumber,
        @Schema(description = "단식 매치 리스트")
        List<SinglesMatchResponse> singlesMatchResponseList,
        @Schema(description = "복식 매치 리스트")
        List<DoublesMatchResponse> doublesMatchResponseList
) {

    // TODO: 분기를 없애기
    public static BracketResponse fromBracketInfo(BracketInfo bracketInfo) {
        if (bracketInfo.matchType() == MatchType.SINGLES) {
            return new BracketResponse(bracketInfo.leagueId(),
                    BracketType.FREE,
                    bracketInfo.matchType(),
                    bracketInfo.matchStatus(),
                    bracketInfo.roundNumber(),
                    bracketInfo.singlesMatchInfoList().stream()
                            .map(SinglesMatchResponse::fromSinglesMatchInfo)
                            .toList(),
                    null);
        } else if (bracketInfo.matchType() == MatchType.DOUBLES) {
            return new BracketResponse(bracketInfo.leagueId(),
                    BracketType.FREE,
                    bracketInfo.matchType(),
                    bracketInfo.matchStatus(),
                    bracketInfo.roundNumber(),
                    null,
                    bracketInfo.doublesMatchInfoList().stream()
                            .map(DoublesMatchResponse::fromDoublesMatchInfo)
                            .toList());
        }
        return null;
    }

}
