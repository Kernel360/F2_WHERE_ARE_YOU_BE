package org.badminton.api.interfaces.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;

public record LeagueSetsScoreInProgressResponse(
        @Schema(description = "매치 아이디", requiredMode = RequiredMode.REQUIRED)
        Long matchId,
        @Schema(description = "단식 경기 참여자")
        SinglesMatchPlayerResponse singlesMatchPlayerResponse,
        @Schema(description = "복식 경기 참여자")
        DoublesMatchPlayerResponse doublesMatchPlayerResponse,
        @Schema(description = "세트 스코어 1", requiredMode = RequiredMode.REQUIRED)
        int setScore1,
        @Schema(description = "세트 스코어 2", requiredMode = RequiredMode.REQUIRED)
        int setScore2,
        @Schema(description = "라운드 번호(단식이면 1, 2, 3, ... | 복식이면 ... 32, 16, 8, 4, 2, 1", requiredMode = RequiredMode.REQUIRED)
        int roundNumber,
        @Schema(description = "세트 번호 (1 | 2 | 3)", requiredMode = RequiredMode.REQUIRED)
        int setNumber
) {

    public static LeagueSetsScoreInProgressResponse from(LeagueSetsScoreInProgressInfo info) {
        return new LeagueSetsScoreInProgressResponse(
                info.matchId(),
                SinglesMatchPlayerResponse.from(info.singlesMatchPlayerInfo()),
                DoublesMatchPlayerResponse.of(info.doublesMatchPlayerInfo()),
                info.setScore1(),
                info.setScore2(),
                info.roundNumber(),
                info.setNumber()
        );
    }
}
