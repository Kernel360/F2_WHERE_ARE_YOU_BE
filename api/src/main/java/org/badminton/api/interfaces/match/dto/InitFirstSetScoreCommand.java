package org.badminton.api.interfaces.match.dto;

public record InitFirstSetScoreCommand(
	String clubToken,
	Long leagueId,
	Long matchId
) {
}
