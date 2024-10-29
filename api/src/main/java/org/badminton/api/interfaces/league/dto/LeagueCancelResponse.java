package org.badminton.api.interfaces.league.dto;

import org.badminton.domain.domain.league.enums.LeagueStatus;

public record LeagueCancelResponse(
        Long leagueId,
        LeagueStatus leagueStatus
) {
}
