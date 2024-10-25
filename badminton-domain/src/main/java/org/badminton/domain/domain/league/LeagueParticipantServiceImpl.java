package org.badminton.domain.domain.league;

import org.badminton.domain.infrastructures.league.LeagueParticipantRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueParticipantServiceImpl implements LeagueParticipantService {
	private final LeagueParticipantRepository leagueParticipantRepository;

	@Override
	public boolean isParticipant(String memberToken, Long leagueId) {
		return !leagueParticipantRepository
			.findByMemberMemberTokenAndLeagueLeagueIdAndCanceledFalse(memberToken, leagueId).isEmpty();
	}

	@Override
	public int countParticipantMember(Long leagueId) {
		return leagueParticipantRepository.countByLeagueLeagueIdAndCanceledFalse(leagueId);
	}
}
