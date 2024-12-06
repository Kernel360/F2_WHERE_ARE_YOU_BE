package org.badminton.domain.domain.league;

import java.util.List;

import org.badminton.domain.domain.league.info.IsLeagueParticipantInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantCancelInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantDetailsInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantInfo;

public interface LeagueParticipantService {
	IsLeagueParticipantInfo isParticipant(String memberToken, Long leagueId);

	int countParticipantMember(Long leagueId);

	LeagueParticipantCancelInfo cancelLeagueParticipation(String memberToken, String clubToken, Long leagueId);

	LeagueParticipantInfo participantInLeague(String memberToken, String clubToken, Long leagueId);

	List<LeagueParticipantDetailsInfo> getLeagueParticipants(Long leagueId);
}
