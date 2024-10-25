package org.badminton.domain.domain.league;

public interface LeagueParticipantService {
	boolean isParticipant(String memberToken, Long leagueId);

	int countParticipantMember(Long leagueId);
}
