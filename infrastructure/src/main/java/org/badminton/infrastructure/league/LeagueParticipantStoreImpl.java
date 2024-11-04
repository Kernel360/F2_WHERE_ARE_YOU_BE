package org.badminton.infrastructure.league;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.league.LeagueParticipantStore;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueParticipantStoreImpl implements LeagueParticipantStore {
	private final LeagueParticipantRepository leagueParticipantRepository;

	@Override
	public LeagueParticipant store(ClubMember clubMember, League league) {
		LeagueParticipant leagueParticipant = new LeagueParticipant(clubMember, league);
		return leagueParticipantRepository.save(leagueParticipant);
	}

	@Override
	public LeagueParticipant cancelStore(LeagueParticipant leagueParticipant) {
		leagueParticipant.cancelLeagueParticipation();
		return leagueParticipantRepository.save(leagueParticipant);
	}
}
