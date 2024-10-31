package org.badminton.api.interfaces.match.dto;

import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;

public record LeagueSetsScoreInProgressResponse(
        Long matchId,
        SinglesMatchPlayerResponse singlesMatchPlayerResponse,
        DoublesMatchPlayerResponse doublesMatchPlayerResponse,
        int setScore1,
        int setScore2,
        int roundNumber,
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
