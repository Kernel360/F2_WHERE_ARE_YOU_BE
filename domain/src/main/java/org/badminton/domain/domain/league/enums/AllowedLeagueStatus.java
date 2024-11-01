package org.badminton.domain.domain.league.enums;

import lombok.Getter;

@Getter
public enum AllowedLeagueStatus {
    ALL(LeagueStatus.ALL),
    RECRUITING(LeagueStatus.RECRUITING),
    PLAYING(LeagueStatus.PLAYING);

    private final LeagueStatus status;

    AllowedLeagueStatus(LeagueStatus status) {
        this.status = status;
    }

}
