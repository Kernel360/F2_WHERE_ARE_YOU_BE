package org.badminton.api.application.league;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.badminton.domain.domain.league.LeagueParticipantService;
import org.badminton.domain.domain.league.LeagueService;
import org.badminton.domain.domain.league.command.LeagueCreateNoIncludeClubCommand;
import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
import org.badminton.domain.domain.league.info.LeagueByDateInfo;
import org.badminton.domain.domain.league.info.LeagueByDateInfoWithParticipantCountInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.badminton.domain.domain.league.info.LeagueDetailsInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.badminton.domain.domain.league.info.LeagueSummaryInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfoWithParticipantCountInfo;
import org.badminton.domain.domain.match.service.MatchRetrieveService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueFacade {
    private final LeagueService leagueService;
    private final LeagueParticipantService leagueParticipantService;
    private final MatchRetrieveService matchRetrieveService;

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
        LeagueCreateInfo leagueCreateInfo = leagueService.createLeague(memberToken, clubToken,
                leagueCreateNoIncludeClubCommand);
        leagueParticipantService.participantInLeague(memberToken, clubToken, leagueCreateInfo.leagueId());
        return leagueCreateInfo;
    }

    public LeagueDetailsInfo getLeague(String clubToken, Long leagueId, String memberToken) {
        LeagueSummaryInfo leagueSummaryInfo = leagueService.getLeague(clubToken, leagueId);
        MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
        boolean isMatchCreated = matchRetrieveService.isMatchInLeague(matchStrategy, leagueId);
        boolean isParticipatedInLeague = leagueParticipantService.isParticipant(memberToken, leagueId);
        int recruitedMemberCount = leagueParticipantService.countParticipantMember(leagueId);
        return LeagueDetailsInfo.from(leagueSummaryInfo, isMatchCreated, isParticipatedInLeague, recruitedMemberCount);
    }

    public LeagueUpdateInfoWithParticipantCountInfo updateLeague(String clubToken, Long leagueId,
                                                                 LeagueUpdateCommand leagueUpdateCommand) {
        LeagueUpdateInfo leagueUpdateInfo = leagueService.updateLeague(clubToken, leagueId, leagueUpdateCommand);
        int recruitedMemberCount = leagueParticipantService.countParticipantMember(leagueId);
        return LeagueUpdateInfoWithParticipantCountInfo.of(leagueUpdateInfo, recruitedMemberCount);
    }

    public LeagueCancelInfo cancelLeague(String clubToken, Long leagueId) {
        return leagueService.cancelLeague(clubToken, leagueId);
    }

}
