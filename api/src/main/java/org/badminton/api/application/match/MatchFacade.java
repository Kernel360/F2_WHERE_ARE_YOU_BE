package org.badminton.api.application.match;

import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.badminton.api.interfaces.match.dto.SetScoreUpdateRequest;
import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.match.SetScoreNotInCacheException;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.service.MatchRetrieveService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.badminton.domain.domain.match.vo.RedisKey;
import org.badminton.domain.domain.match.vo.Score;
import org.badminton.infrastructure.match.repository.SetRepository;
import org.badminton.infrastructure.match.service.RetrieveMatchSet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchFacade {

    private final MatchOperationHandler freeMatchFacade;
    private final MatchOperationHandler tournamentMatchFacade;
    private final MatchRetrieveService freeMatchRetrieveService;
    private final MatchRetrieveService tournamentMatchRetrieveService;
    private final LeagueReader leagueReader;
    private final RetrieveMatchSet retrieveMatchSet;
    private final SetRepository setRepository;

    public MatchFacade(
            @Qualifier("freeMatchFacade") MatchOperationHandler freeMatchFacade,
            @Qualifier("tournamentMatchFacade") MatchOperationHandler tournamentMatchFacade,
            @Qualifier("freeMatchRetrieveServiceImpl") MatchRetrieveService freeMatchRetrieveService,
            @Qualifier("tournamentMatchRetrieveServiceImpl") MatchRetrieveService tournamentMatchRetrieveService,
            LeagueReader leagueReader, RetrieveMatchSet retrieveMatchSet, SetRepository setRepository) {
        this.freeMatchFacade = freeMatchFacade;
        this.tournamentMatchFacade = tournamentMatchFacade;
        this.freeMatchRetrieveService = freeMatchRetrieveService;
        this.tournamentMatchRetrieveService = tournamentMatchRetrieveService;
        this.leagueReader = leagueReader;
        this.retrieveMatchSet = retrieveMatchSet;
        this.setRepository = setRepository;
    }

    @Scheduled(fixedRate = 100000)
    public void saveInDb() {
        Map<RedisKey, Score> allScores = setRepository.getAllScores();
        for (RedisKey key : allScores.keySet()) {
            retrieveMatchSet.registerMatchSetScoreInDb(key, allScores.get(key));
        }
    }

    public MatchOperationHandler getMatchOperationHandler(Long leagueId) {
        MatchGenerationType matchGenerationType = leagueReader.getMatchGenerationTypeByLeagueId(leagueId);
        return matchGenerationType == MatchGenerationType.FREE ? freeMatchFacade : tournamentMatchFacade;
    }

    private MatchRetrieveService getMatchRetrieveService(Long leagueId) {
        MatchGenerationType matchGenerationType = leagueReader.getMatchGenerationTypeByLeagueId(leagueId);
        return matchGenerationType == MatchGenerationType.FREE ? freeMatchRetrieveService :
                tournamentMatchRetrieveService;
    }

    public BracketInfo retrieveBracket(Long leagueId) {
        MatchRetrieveService matchRetrieveService = getMatchRetrieveService(leagueId);
        MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
        return matchRetrieveService.retrieveBracket(matchStrategy, leagueId);
    }

    public List<SetInfo.Main> retrieveAllSetsScoreInBracket(Long leagueId) {
        MatchRetrieveService matchRetrieveService = getMatchRetrieveService(leagueId);
        MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
        return matchRetrieveService.retrieveAllSetsScoreInBracket(matchStrategy, leagueId);
    }

    public MatchInfo.SetScoreDetails retrieveAllSetsScoreInMatch(Long leagueId, Long matchId) {
        MatchRetrieveService matchRetrieveService = getMatchRetrieveService(leagueId);
        MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
        return matchRetrieveService.retrieveAllSetsScoreInMatch(matchStrategy, matchId);
    }

    public List<LeagueSetsScoreInProgressInfo> retrieveLeagueMatchSetsScoreInProgress(Long leagueId) {
        MatchRetrieveService matchRetrieveService = getMatchRetrieveService(leagueId);
        MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(leagueId);
        return matchRetrieveService.retrieveLeagueMatchInProgress(matchStrategy, leagueId);
    }

    public SetInfo.Main registerSetScore(Long leagueId, Long matchId, int setNumber,
                                         SetScoreUpdateRequest setScoreUpdateRequest, String memberToken) {
        retrieveMatchSet.setMatchSetScore(leagueId, matchId, setNumber,
                new Score(setScoreUpdateRequest.score1(), setScoreUpdateRequest.score2()), memberToken);
        return retrieveSetInfo(leagueId, matchId, setNumber);
    }

    public SetInfo.Main retrieveSetInfo(Long leagueId, Long matchId, int setNumber) {
        try {
            Score score = retrieveMatchSet.getMatchSetScore(leagueId, matchId, setNumber);
            MatchType matchType = retrieveMatchSet.getMatchType(leagueId);
            return SetInfo.Main.builder()
                    .matchId(matchId)
                    .score1(score.getLeft())
                    .score2(score.getRight())
                    .matchType(matchType)
                    .setNumber(setNumber)
                    .build();
        } catch (SetScoreNotInCacheException e) {
            MatchRetrieveService matchRetrieveService = getMatchRetrieveService(leagueId);
            MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(matchId);
            return matchRetrieveService.retrieveSet(matchStrategy, matchId, setNumber);
        }
    }

    public MatchSetInfo retrieveMatchSetInfo(Long leagueId, Long matchId, int setNumber) {
        MatchRetrieveService matchRetrieveService = getMatchRetrieveService(leagueId);
        MatchStrategy matchStrategy = matchRetrieveService.makeSinglesOrDoublesMatchStrategy(matchId);
        return matchRetrieveService.retrieveMatchSet(matchStrategy, matchId, setNumber);
    }
}
