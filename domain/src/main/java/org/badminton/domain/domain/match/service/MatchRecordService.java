package org.badminton.domain.domain.match.service;

import lombok.RequiredArgsConstructor;
import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.service.LeagueRecordService;
import org.badminton.domain.domain.league.vo.Team;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchRecordService {

    private final LeagueRecordService leagueRecordService;
    private final SinglesMatchReader singlesMatchReader;
    private final DoublesMatchReader doublesMatchReader;

    public void processMatchResult(MatchType matchType, Long matchId) {
        if (matchType == MatchType.SINGLES) {
            processSinglesMatchResult(matchId);
        } else if (matchType == MatchType.DOUBLES) {
            processDoublesMatchResult(matchId);
        }
    }

    private void processSinglesMatchResult(Long matchId) {
        SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
        MatchResult player1Result = singlesMatch.getPlayer1MatchResult();

        if (player1Result == MatchResult.WIN || player1Result == MatchResult.LOSE) {
            String winnerToken = (player1Result == MatchResult.WIN) ?
                    singlesMatch.getLeagueParticipant1().getMember().getMemberToken() :
                    singlesMatch.getLeagueParticipant2().getMember().getMemberToken();
            String loserToken = (player1Result == MatchResult.WIN) ?
                    singlesMatch.getLeagueParticipant2().getMember().getMemberToken() :
                    singlesMatch.getLeagueParticipant1().getMember().getMemberToken();

            leagueRecordService.addWin(winnerToken);
            leagueRecordService.addLoss(loserToken);
        }
    }

    private void processDoublesMatchResult(Long matchId) {
        DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
        MatchResult team1Result = doublesMatch.getTeam1MatchResult();

        if (team1Result == MatchResult.WIN || team1Result == MatchResult.LOSE) {
            String[] winnerTokens = (team1Result == MatchResult.WIN) ?
                    getTeamMemberTokens(doublesMatch.getTeam1()) :
                    getTeamMemberTokens(doublesMatch.getTeam2());
            String[] loserTokens = (team1Result == MatchResult.WIN) ?
                    getTeamMemberTokens(doublesMatch.getTeam2()) :
                    getTeamMemberTokens(doublesMatch.getTeam1());

            addMatchResults(winnerTokens, loserTokens);
        }
    }

    private String[] getTeamMemberTokens(Team team) {
        return new String[]{
                team.getLeagueParticipant1().getMember().getMemberToken(),
                team.getLeagueParticipant2().getMember().getMemberToken()
        };
    }

    private void addMatchResults(String[] winnerTokens, String[] loserTokens) {
        for (String winnerToken : winnerTokens) {
            leagueRecordService.addWin(winnerToken);
        }
        for (String loserToken : loserTokens) {
            leagueRecordService.addLoss(loserToken);
        }
    }
}