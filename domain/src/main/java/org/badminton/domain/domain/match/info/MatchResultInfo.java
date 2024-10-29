package org.badminton.domain.domain.match.info;

import java.time.LocalDateTime;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;

public record MatchResultInfo(
        Long matchId,
        Long leagueId,
        MatchType matchType,
        SinglesMatchResultInfo singlesMatch,
        DoublesMatchResultInfo doublesMatch,
        MatchStatus matchStatus,
        LocalDateTime leagueAt
) {

    public static MatchResultInfo fromSinglesMatch(SinglesMatch singlesMatch, Long clubMemberId) {
        boolean isPlayer1 = singlesMatch.getLeagueParticipant1().getClubMember().getClubMemberId().equals(clubMemberId);
        return new MatchResultInfo(
                singlesMatch.getId(),
                singlesMatch.getLeague().getLeagueId(),
                MatchType.SINGLES,
                SinglesMatchResultInfo.fromSinglesMatch(singlesMatch, isPlayer1, clubMemberId),
                null,
                singlesMatch.getMatchStatus()
                , singlesMatch.getLeague().getLeagueAt()
        );
    }

    public static MatchResultInfo fromDoublesMatch(DoublesMatch doublesMatch, Long clubMemberId) {
        return new MatchResultInfo(
                doublesMatch.getId(),
                doublesMatch.getLeague().getLeagueId(),
                MatchType.DOUBLES,
                null,
                DoublesMatchResultInfo.fromDoublesMatch(doublesMatch, clubMemberId),
                doublesMatch.getMatchStatus(),
                doublesMatch.getLeague().getLeagueAt()
        );
    }
}