package org.badminton.api.application.league;

import org.badminton.domain.domain.clubmember.service.ClubMemberService;
import org.badminton.domain.domain.league.LeagueParticipantService;
import org.badminton.domain.domain.league.info.LeagueParticipantCancelInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantInfo;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueParticipationFacade {
	private final LeagueParticipantService leagueParticipantService;
	private final ClubMemberService clubMemberService;

	public LeagueParticipantInfo participateInLeague(String memberToken, String clubToken, Long leagueId) {
		return leagueParticipantService.participantInLeague(memberToken, clubToken, leagueId);
	}

	public LeagueParticipantCancelInfo cancelLeagueParticipation(String clubToken, String memberToken, Long leagueId) {
		var clubMember = clubMemberService.getClubMemberDetailByClubToken(clubToken, memberToken);
		Long clubMemberId = clubMember.clubMemberId();
		return leagueParticipantService.participantLeagueCancel(leagueId, clubMemberId);
	}
}
