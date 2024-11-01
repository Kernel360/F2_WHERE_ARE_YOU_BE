package org.badminton.domain.domain.league;

import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.league.entity.League;
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
	private final LeagueReader leagueReader;
	private final ClubMemberReader clubMemberReader;

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
	public LeagueParticipantCancelInfo participantLeagueCancel(String memberToken, String clubToken, Long leagueId) {
		ClubMember clubMember = clubMemberReader.getClubMemberByMemberTokenAndClubToken(clubToken, memberToken);
		Long clubMemberId = clubMember.getClubMemberId();
		LeagueParticipant leagueParticipant = leagueParticipantReader.findParticipant(leagueId, clubMemberId);
		var result = leagueParticipantStore.cancelStore(leagueParticipant);
		return LeagueParticipantCancelInfo.from(result);
	}

	@Override
	public LeagueParticipantInfo participantInLeague(String memberToken, String clubToken, Long leagueId) {
		ClubMember clubMember = clubMemberReader.getClubMemberByMemberTokenAndClubToken(clubToken, memberToken);
		League league = leagueReader.readLeagueById(leagueId);
		return LeagueParticipantInfo.from(leagueParticipantStore.store(clubMember, league));
	}

}
