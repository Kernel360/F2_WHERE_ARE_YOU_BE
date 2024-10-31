package org.badminton.api.application.match;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.service.BracketGenerationService;
import org.badminton.domain.domain.match.service.MatchProgressService;
import org.badminton.domain.domain.match.service.MatchRetrieveService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchFacade {
    private final BracketGenerationService bracketGenerationService;
    private final MatchRetrieveService matchRetrieveService;
    private final MatchProgressService matchProgressService;

    public BracketInfo generateInitialBracket(Long leagueId) {
        bracketGenerationService.checkLeagueRecruitingStatus(leagueId);
        MatchStrategy matchStrategy = bracketGenerationService.makeSinglesOrDoublesMatchStrategy(leagueId);
        return bracketGenerationService.makeInitialBracket(matchStrategy, leagueId);
    }

    public BracketInfo retrieveBracket(Long leagueId) {
        MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
        return matchRetrieveService.retrieveBracket(matchStrategy, leagueId);
    }

    public List<SetInfo.Main> retrieveAllSetsScoreInBracket(Long leagueId) {
        MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
        return matchRetrieveService.retrieveAllSetsScoreInBracket(matchStrategy, leagueId);
    }

    public MatchInfo.SetScoreDetails retrieveAllSetsScoreInMatch(Long leagueId, Long matchId) {
        MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
        return matchRetrieveService.retrieveAllSetsScoreInMatch(matchStrategy, matchId);
    }

    public SetInfo.Main registerSetScoreInMatch(Long leagueId, Long matchId, int setIndex,
                                                MatchCommand.UpdateSetScore updateSetScoreCommand) {
        MatchStrategy matchStrategy = matchProgressService.makeSinglesOrDoublesMatchStrategy(leagueId);
        return matchProgressService.registerSetScoreInMatch(matchStrategy, leagueId, matchId, setIndex,
                updateSetScoreCommand);
    }

    public List<LeagueSetsScoreInProgressInfo> retrieveLeagueMatchSetsScoreInProgress(Long leagueId) {
        MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
        return matchRetrieveService.retrieveLeagueMatchInProgress(matchStrategy, leagueId);
    }

    public MatchSetInfo retrieveMatchSetInfo(Long matchId, int setNumber) {
        MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(matchId);
        return matchRetrieveService.retrieveMatchSet(matchStrategy, matchId, setNumber);
    }
}
