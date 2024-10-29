package org.badminton.domain.domain.league;

import org.badminton.domain.domain.clubmember.command.ClubMemberMyPageCommand;
import org.badminton.domain.domain.league.command.LeagueDetailCommand;
import org.badminton.domain.domain.league.info.LeagueParticipantCancelInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantInfo;

public interface LeagueParticipantService {
	boolean isParticipant(String memberToken, Long leagueId);

	int countParticipantMember(Long leagueId);

	LeagueParticipantInfo participantLeague(ClubMemberMyPageCommand clubMember, LeagueDetailCommand league);

	LeagueParticipantCancelInfo participantLeagueCancel(Long leagueId, Long clubMemberId);
}
