package org.badminton.domain.domain.league;

import org.badminton.domain.domain.clubmember.command.ClubMemberMyPageCommand;
import org.badminton.domain.domain.league.command.LeagueDetailCommand;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.info.LeagueParticipantCancelInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantInfo;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueParticipantServiceImpl implements LeagueParticipantService {
	private final LeagueParticipantReader leagueParticipantReader;
	private final LeagueParticipantStore leagueParticipantStore;

	@Override
	@Transactional
	public boolean isParticipant(String memberToken, Long leagueId) {
		return leagueParticipantReader.isParticipant(memberToken, leagueId);
	}

	@Override
	@Transactional
	public int countParticipantMember(Long leagueId) {
		return leagueParticipantReader.countParticipantMember(leagueId);
	}

	@Override
	@Transactional
	public LeagueParticipantInfo participantLeague(ClubMemberMyPageCommand clubMember, LeagueDetailCommand league) {
		var saved = leagueParticipantStore.store(clubMember, league);
		return LeagueParticipantInfo.from(saved);
	}

	@Override
	public LeagueParticipantCancelInfo participantLeagueCancel(Long leagueId, Long clubMemberId) {
		LeagueParticipant leagueParticipant = leagueParticipantReader.findParticipant(leagueId, clubMemberId);
		var result = leagueParticipantStore.cancelStore(leagueParticipant);
		return LeagueParticipantCancelInfo.from(result);
	}

}
