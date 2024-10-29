package org.badminton.domain.domain.league;

import org.badminton.domain.domain.clubmember.command.ClubMemberMyPageCommand;
import org.badminton.domain.domain.league.command.LeagueDetailCommand;
import org.badminton.domain.domain.league.entity.LeagueParticipant;

public interface LeagueParticipantStore {

	LeagueParticipant store(ClubMemberMyPageCommand clubMember, LeagueDetailCommand league);

	LeagueParticipant cancelStore(LeagueParticipant leagueParticipant);
}
