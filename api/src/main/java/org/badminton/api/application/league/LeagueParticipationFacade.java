package org.badminton.api.application.league;

import org.badminton.domain.common.policy.ClubMemberPolicy;
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
	private final ClubMemberPolicy clubMemberPolicy;

	public LeagueParticipantInfo participateInLeague(String memberToken, String clubToken, Long leagueId) {
		clubMemberPolicy.validateClubMember(memberToken, clubToken);
		return leagueParticipantService.participantInLeague(memberToken, clubToken, leagueId);
	}

	public LeagueParticipantCancelInfo cancelParticipateInLeague(String clubToken, String memberToken, Long leagueId) {
		clubMemberPolicy.validateClubMember(memberToken, clubToken);
		return leagueParticipantService.cancelLeagueParticipation(memberToken, clubToken, leagueId);
	}
}
