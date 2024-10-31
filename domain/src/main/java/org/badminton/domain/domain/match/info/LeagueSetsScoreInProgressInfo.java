package org.badminton.domain.domain.match.info;

import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.entity.SinglesSet;

public record LeagueSetsScoreInProgressInfo(
        Long matchId,
        SinglesMatchPlayerInfo singlesMatchPlayerInfo,
        DoublesMatchPlayerInfo doublesMatchPlayerInfo,
        int setScore1,
        int setScore2,
        int roundNumber,
        int setNumber
) {

    public static LeagueSetsScoreInProgressInfo fromSinglesMatchAndSet(SinglesMatch singlesMatch,
                                                                       SinglesSet singlesSet) {
        return new LeagueSetsScoreInProgressInfo(singlesMatch.getId(),
                new SinglesMatchPlayerInfo(singlesMatch.getLeagueParticipant1().getMember().getName(),
                        singlesMatch.getLeagueParticipant1().getMember().getProfileImage(),
                        singlesMatch.getLeagueParticipant2().getMember().getName(),
                        singlesMatch.getLeagueParticipant2().getMember().getProfileImage()),
                null,
                singlesSet.getPlayer1Score(),
                singlesSet.getPlayer2Score(),
                singlesMatch.getRoundNumber(),
                singlesSet.getSetIndex());
    }

    public static LeagueSetsScoreInProgressInfo fromDoublesMatchAndSet(DoublesMatch doublesMatch,
                                                                       DoublesSet doublesSet) {
        return new LeagueSetsScoreInProgressInfo(doublesMatch.getId(),
                null,
                DoublesMatchPlayerInfo.of(doublesMatch.getTeam1(), doublesMatch.getTeam2()),
                doublesSet.getTeam1Score(),
                doublesSet.getTeam2Score(),
                doublesMatch.getRoundNumber(),
                doublesSet.getSetNumber());
    }
}
