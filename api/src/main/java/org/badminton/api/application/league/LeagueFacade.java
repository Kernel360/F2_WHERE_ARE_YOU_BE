package org.badminton.api.application.league;

import java.util.List;
import java.util.stream.Collectors;

import org.badminton.domain.common.policy.ClubMemberPolicy;
import org.badminton.domain.domain.league.LeagueParticipantService;
import org.badminton.domain.domain.league.LeagueService;
import org.badminton.domain.domain.league.command.LeagueCreateNoIncludeClubCommand;
import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
import org.badminton.domain.domain.league.info.IsLeagueParticipantInfo;
import org.badminton.domain.domain.league.info.LeagueByDateInfo;
import org.badminton.domain.domain.league.info.LeagueByDateInfoWithParticipantCountInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.badminton.domain.domain.league.info.LeagueDetailsInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantDetailsInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.badminton.domain.domain.league.info.LeagueRecruitingCompleteInfo;
import org.badminton.domain.domain.league.info.LeagueSummaryInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfoWithParticipantCountInfo;
import org.badminton.domain.domain.match.service.MatchRetrieveService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LeagueFacade {
	private final LeagueService leagueService;
	private final LeagueParticipantService leagueParticipantService;
	private final MatchRetrieveService matchRetrieveService;
	private final ClubMemberPolicy clubMemberPolicy;

	public LeagueFacade(LeagueService leagueService, LeagueParticipantService leagueParticipantService,
		@Qualifier("freeMatchRetrieveServiceImpl") MatchRetrieveService matchRetrieveService,
		ClubMemberPolicy clubMemberPolicy) {
		this.leagueService = leagueService;
		this.leagueParticipantService = leagueParticipantService;
		this.matchRetrieveService = matchRetrieveService;
		this.clubMemberPolicy = clubMemberPolicy;
	}

	public List<LeagueReadInfo> getLeaguesByMonth(String clubToken, String date) {
		return leagueService.getLeaguesByMonth(clubToken, date);
	}

	public List<LeagueByDateInfoWithParticipantCountInfo> getLeaguesByDate(String clubToken, String date) {
		List<LeagueByDateInfo> leagueByDateInfoList = leagueService.getLeaguesByDate(clubToken, date);
		return leagueByDateInfoList.stream().map((leagueByDateInfo -> {
			int participantCount = leagueParticipantService.countParticipantMember(leagueByDateInfo.leagueId());
			return new LeagueByDateInfoWithParticipantCountInfo(leagueByDateInfo, participantCount);
		})).collect(Collectors.toList());
	}

	public LeagueCreateInfo createLeague(String memberToken, String clubToken,
		LeagueCreateNoIncludeClubCommand leagueCreateNoIncludeClubCommand) {
		clubMemberPolicy.validateClubMember(memberToken, clubToken);
		LeagueCreateInfo leagueCreateInfo = leagueService.createLeague(memberToken, clubToken,
			leagueCreateNoIncludeClubCommand);
		leagueParticipantService.participantInLeague(memberToken, clubToken, leagueCreateInfo.leagueId());
		return leagueCreateInfo;
	}

	public LeagueDetailsInfo getLeague(String clubToken, Long leagueId) {
		LeagueSummaryInfo leagueSummaryInfo = leagueService.getLeague(clubToken, leagueId);
		MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
		boolean isMatchCreated = matchRetrieveService.isMatchInLeague(matchStrategy, leagueId);
		int recruitedMemberCount = leagueParticipantService.countParticipantMember(leagueId);
		List<LeagueParticipantDetailsInfo> leagueParticipants = leagueParticipantService.getLeagueParticipants(
			leagueId);
		return LeagueDetailsInfo.from(leagueSummaryInfo, isMatchCreated, recruitedMemberCount, leagueParticipants);
	}

	public IsLeagueParticipantInfo isLeagueParticipant(String memberToken, Long leagueId) {
		return leagueParticipantService.isParticipant(memberToken, leagueId);
	}

	public LeagueUpdateInfoWithParticipantCountInfo updateLeague(String clubToken, Long leagueId,
		LeagueUpdateCommand leagueUpdateCommand, String memberToken) {
		clubMemberPolicy.validateClubMember(memberToken, clubToken);
		LeagueUpdateInfo leagueUpdateInfo = leagueService.updateLeague(clubToken, leagueId, leagueUpdateCommand,
			memberToken);
		int recruitedMemberCount = leagueParticipantService.countParticipantMember(leagueId);
		return LeagueUpdateInfoWithParticipantCountInfo.of(leagueUpdateInfo, recruitedMemberCount);
	}

	public LeagueCancelInfo cancelLeague(String clubToken, Long leagueId, String memberToken) {
		clubMemberPolicy.validateClubMember(memberToken, clubToken);
		return leagueService.cancelLeague(clubToken, leagueId, memberToken);
	}

	public LeagueRecruitingCompleteInfo completeLeagueRecruiting(String clubToken, Long leagueId, String memberToken) {
		clubMemberPolicy.validateClubMember(memberToken, clubToken);
		return leagueService.completeLeagueRecruiting(clubToken, leagueId, memberToken);
	}
}
