package org.badminton.domain.infrastructures.league;

import org.badminton.domain.domain.clubmember.command.ClubMemberMyPageCommand;
import org.badminton.domain.domain.league.LeagueParticipantStore;
import org.badminton.domain.domain.league.command.LeagueDetailCommand;
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
	public LeagueParticipant store(ClubMemberMyPageCommand clubMember, LeagueDetailCommand league) {
		var clubMemberEntity = clubMember.toEntity();
		var leagueEntity = league.toEntity();
		LeagueParticipant leagueParticipant = new LeagueParticipant(clubMemberEntity, leagueEntity);
		return leagueParticipantRepository.save(leagueParticipant);
	}

	@Override
	public LeagueParticipant cancelStore(LeagueParticipant leagueParticipant) {
		leagueParticipant.cancelLeagueParticipation();
		return leagueParticipantRepository.save(leagueParticipant);
	}
}
