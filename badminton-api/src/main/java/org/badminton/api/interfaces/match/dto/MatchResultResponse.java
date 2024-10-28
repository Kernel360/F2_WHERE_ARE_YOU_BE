package org.badminton.api.interfaces.match.dto;

import java.time.LocalDateTime;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.match.info.MatchResultInfo;

public record MatchResultResponse(
        Long matchId,
        Long leagueId,
        MatchType matchType,
        SinglesMatchResultResponse singlesMatch,
        DoublesMatchResultResponse doublesMatch,
        MatchStatus matchStatus,
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