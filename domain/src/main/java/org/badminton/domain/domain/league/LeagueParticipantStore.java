package org.badminton.domain.domain.league;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;

public interface LeagueParticipantStore {

	LeagueParticipant store(ClubMember clubMember, League league);

	LeagueParticipant cancelStore(LeagueParticipant leagueParticipant);
}
