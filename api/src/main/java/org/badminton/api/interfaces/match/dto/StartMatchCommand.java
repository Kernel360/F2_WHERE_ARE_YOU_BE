package org.badminton.api.interfaces.match.dto;

public record StartMatchCommand(
        String clubToken,
        Long leagueId,
        Long matchId
) {
}
