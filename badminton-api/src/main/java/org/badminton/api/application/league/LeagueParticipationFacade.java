package org.badminton.api.application.league;

import org.badminton.domain.domain.clubmember.command.ClubMemberMyPageCommand;
import org.badminton.domain.domain.clubmember.service.ClubMemberService;
import org.badminton.domain.domain.league.LeagueParticipantService;
import org.badminton.domain.domain.league.LeagueService;
import org.badminton.domain.domain.league.command.LeagueDetailCommand;
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
	private final LeagueService leagueService;

	public LeagueParticipantInfo participateInLeague(String memberToken, String clubToken, Long leagueId) {
		var league = leagueService.getLeagueDetail(clubToken, leagueId);
		var clubMember = clubMemberService.getClubMemberDetail(memberToken);
		var commandLeague = LeagueDetailCommand.toCommand(league);
		var commandClubMember = ClubMemberMyPageCommand.toCommand(clubMember);
		return leagueParticipantService.participantLeague(commandClubMember, commandLeague);
	}

	public LeagueParticipantCancelInfo cancelLeagueParticipation(String clubToken, String memberToken, Long leagueId) {
		var clubMember = clubMemberService.getClubMemberDetailByClubToken(clubToken, memberToken);
		Long clubMemberId = clubMember.clubMemberId();
		return leagueParticipantService.participantLeagueCancel(leagueId, clubMemberId);
	}
}
