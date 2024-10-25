package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueByDateResponse(
        Long leagueId,
        LocalDateTime leagueAt,
        String leagueName,
        MatchType matchType,
        Member.MemberTier requiredTier,
        LocalDateTime closedAt,
        int playerLimitCount,
        int recruitedMemberCount
) {

    public static LeagueByDateResponse fromLeagueEntity(League league, int recruitedMemberCount) {
        return new LeagueByDateResponse(
                league.getLeagueId(), league.getLeagueAt(), league.getLeagueName(),
                league.getMatchType(), league.getRequiredTier(), league.getRecruitingClosedAt(),
                league.getPlayerLimitCount(), recruitedMemberCount
        );
    }

}
