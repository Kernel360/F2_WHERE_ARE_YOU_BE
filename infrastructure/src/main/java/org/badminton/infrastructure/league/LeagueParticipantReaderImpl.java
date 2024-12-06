package org.badminton.infrastructure.league;

import java.util.List;

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

	// TODO: 경기 참여자가 아예 없는 경우 다른 예외 출력
	@Override
	public LeagueParticipant findParticipant(Long leagueId, Long clubMemberId) {
		return leagueParticipantRepository.findByLeagueLeagueIdAndClubMemberClubMemberIdAndCanceledFalse(leagueId,
			clubMemberId).orElseThrow(() -> new LeagueParticipationAlreadyCanceledException(leagueId, clubMemberId));
	}

	@Override
	public List<LeagueParticipant> findAllByLeagueIdAndCanceledFalse(Long leagueId) {
		return leagueParticipantRepository.findAllByLeagueLeagueIdAndCanceledFalse(leagueId);
	}

}
