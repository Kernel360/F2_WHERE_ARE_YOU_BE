package org.badminton.domain.domain.league;

import org.badminton.domain.domain.league.info.LeagueParticipantCancelInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantInfo;

public interface LeagueParticipantService {
	boolean isParticipant(String memberToken, Long leagueId);

	int countParticipantMember(Long leagueId);

	LeagueParticipantCancelInfo participantLeagueCancel(String memberToken, String clubToken, Long leagueId);

	LeagueParticipantInfo participantInLeague(String memberToken, String clubToken, Long leagueId);
}
