package org.badminton.api.interfaces.match.dto;

import java.time.LocalDateTime;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;

public record MatchResultResponse(
        Long matchId,
        Long leagueId,
        MatchType matchType,
        SinglesMatchResultResponse singlesMatch,
        DoublesMatchResultResponse doublesMatch,
        MatchStatus matchStatus,
        LocalDateTime leagueAt
) {
    public static MatchResultResponse fromSinglesMatch(SinglesMatch singlesMatch, Long clubMemberId) {
        boolean isPlayer1 = singlesMatch.getLeagueParticipant1().getClubMember().getClubMemberId().equals(clubMemberId);
        return new MatchResultResponse(
                singlesMatch.getId(),
                singlesMatch.getLeague().getLeagueId(),
                MatchType.SINGLES,
                SinglesMatchResultResponse.fromSinglesMatch(singlesMatch, isPlayer1, clubMemberId),
                null,
                singlesMatch.getMatchStatus()
                , singlesMatch.getLeague().getLeagueAt()
        );
    }

    public static MatchResultResponse fromDoublesMatch(DoublesMatch doublesMatch, Long clubMemberId) {
        boolean isTeam1 = isPlayerInTeam1(doublesMatch, clubMemberId);
        return new MatchResultResponse(
                doublesMatch.getId(),
                doublesMatch.getLeague().getLeagueId(),
                MatchType.DOUBLES,
                null,
                DoublesMatchResultResponse.fromDoublesMatch(doublesMatch, isTeam1, clubMemberId),
                doublesMatch.getMatchStatus(),
                doublesMatch.getLeague().getLeagueAt()
        );
    }

    private static boolean isPlayerInTeam1(DoublesMatch match, Long clubMemberId) {
        return match.getTeam1().getLeagueParticipant1().getClubMember().getClubMemberId().equals(clubMemberId) ||
                match.getTeam1().getLeagueParticipant2().getClubMember().getClubMemberId().equals(clubMemberId);
    }
}
