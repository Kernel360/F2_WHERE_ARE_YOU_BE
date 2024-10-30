package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;

@AllArgsConstructor
@Getter
public class OngoingAndUpcomingLeagueInfo {

    LocalDateTime leagueAt;
    String leagueName;
    String description;
    MatchType matchType;
    int playerLimitCount;
    int recruitedMemberCount;
    LeagueStatus leagueStatus;

    public static OngoingAndUpcomingLeagueInfo from(League league, int recruitedMemberCount) {
        return new OngoingAndUpcomingLeagueInfo(
                league.getLeagueAt(),
                league.getLeagueName(),
                league.getDescription(),
                league.getMatchType(),
                league.getPlayerLimitCount(),
                recruitedMemberCount,
                league.getLeagueStatus()
        );

    }

}
