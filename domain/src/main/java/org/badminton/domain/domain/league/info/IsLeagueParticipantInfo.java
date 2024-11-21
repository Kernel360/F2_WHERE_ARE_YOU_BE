package org.badminton.domain.domain.league.info;

public record IsLeagueParticipantInfo(
	boolean isParticipatedInLeague
) {
	public static IsLeagueParticipantInfo from(boolean isParticipatedInLeague) {
		return new IsLeagueParticipantInfo(isParticipatedInLeague);
	}
}
