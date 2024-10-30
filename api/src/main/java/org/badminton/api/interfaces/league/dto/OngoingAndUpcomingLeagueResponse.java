package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.league.info.OngoingAndUpcomingLeagueInfo;

public record OngoingAndUpcomingLeagueResponse(
        LocalDateTime leagueAt,
        String leagueName,
        String description,
        MatchType matchType,
        int playerLimitCount,
        int recruitedMemberCount,
        LeagueStatus leagueStatus
) {

    public static OngoingAndUpcomingLeagueResponse from(OngoingAndUpcomingLeagueInfo ongoingAndUpcomingLeagueInfo) {
        return new OngoingAndUpcomingLeagueResponse(ongoingAndUpcomingLeagueInfo.getLeagueAt(),
                ongoingAndUpcomingLeagueInfo.getLeagueName(),
                ongoingAndUpcomingLeagueInfo.getDescription(),
                ongoingAndUpcomingLeagueInfo.getMatchType(),
                ongoingAndUpcomingLeagueInfo.getPlayerLimitCount(),
                ongoingAndUpcomingLeagueInfo.getRecruitedMemberCount(),
                ongoingAndUpcomingLeagueInfo.getLeagueStatus());
    }
}
