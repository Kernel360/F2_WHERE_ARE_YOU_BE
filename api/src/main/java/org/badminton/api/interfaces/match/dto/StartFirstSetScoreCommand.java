package org.badminton.api.interfaces.match.dto;

public record StartFirstSetScoreCommand(
	String clubToken,
	Long leagueId,
	Long matchId
) {
}
