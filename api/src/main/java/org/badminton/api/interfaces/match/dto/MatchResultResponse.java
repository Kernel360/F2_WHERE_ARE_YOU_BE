package org.badminton.api.interfaces.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.LocalDateTime;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.match.info.MatchResultInfo;

public record MatchResultResponse(
        @Schema(description = "매치 아이디", requiredMode = RequiredMode.REQUIRED)
        Long matchId,
        @Schema(description = "경기 아이디", requiredMode = RequiredMode.REQUIRED)
        Long leagueId,
        @Schema(description = "매치 타입", requiredMode = RequiredMode.REQUIRED)
        MatchType matchType,
        @Schema(description = "단식 경기 결과")
        SinglesMatchResultResponse singlesMatch,
        @Schema(description = "복식 경기 결과")
        DoublesMatchResultResponse doublesMatch,
        @Schema(description = "매치 상태 (NOT_STARTED | IN_PROGRESS | FINISHED", requiredMode = RequiredMode.REQUIRED)
        MatchStatus matchStatus,
        @Schema(description = "경기 날짜 및 시간", requiredMode = RequiredMode.REQUIRED)
        LocalDateTime leagueAt
) {

    public static MatchResultResponse from(MatchResultInfo matchResultInfo) {
        if (matchResultInfo.matchType() == MatchType.SINGLES) {
            return new MatchResultResponse(
                    matchResultInfo.matchId(),
                    matchResultInfo.leagueId(),
                    matchResultInfo.matchType(),
                    SinglesMatchResultResponse.from(matchResultInfo.singlesMatch()),
                    null,
                    matchResultInfo.matchStatus(),
                    matchResultInfo.leagueAt()
            );
        } else if (matchResultInfo.matchType() == MatchType.DOUBLES) {
            return new MatchResultResponse(
                    matchResultInfo.matchId(),
                    matchResultInfo.leagueId(),
                    matchResultInfo.matchType(),
                    null,
                    DoublesMatchResultResponse.from(matchResultInfo.doublesMatch()),
                    matchResultInfo.matchStatus(),
                    matchResultInfo.leagueAt()
            );
        } else {
            throw new BadmintonException(ErrorCode.INVALID_PARAMETER);
        }
    }
}