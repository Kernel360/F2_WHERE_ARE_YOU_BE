package org.badminton.domain.infrastructures.league;

import org.badminton.domain.common.exception.league.LeagueParticipationAlreadyCanceledException;
import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueParticipantReaderImpl implements LeagueParticipantReader {
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

	@Override
	public LeagueParticipant findParticipant(Long leagueId, Long clubMemberId) {
		return leagueParticipantRepository.findByLeagueLeagueIdAndClubMemberClubMemberIdAndCanceledFalse(leagueId,
			clubMemberId).orElseThrow(() -> new LeagueParticipationAlreadyCanceledException(leagueId, clubMemberId));
	}
}
