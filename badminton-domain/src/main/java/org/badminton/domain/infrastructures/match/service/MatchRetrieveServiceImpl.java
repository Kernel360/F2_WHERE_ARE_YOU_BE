package org.badminton.domain.infrastructures.match.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.badminton.domain.common.exception.league.LeagueNotExistException;
import org.badminton.domain.domain.league.entity.LeagueEntity;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.MatchInfo.SetScoreDetails;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.service.MatchRetrieveService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.badminton.domain.infrastructures.league.LeagueRepository;
import org.badminton.domain.infrastructures.match.strategy.FreeDoublesMatchStrategy;
import org.badminton.domain.infrastructures.match.strategy.FreeSinglesMatchStrategy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchRetrieveServiceImpl implements MatchRetrieveService {

    private final LeagueRepository leagueRepository;
    private final SinglesMatchReader singlesMatchReader;
    private final DoublesMatchReader doublesMatchReader;
    private final SinglesMatchStore singlesMatchStore;
    private final DoublesMatchStore doublesMatchStore;

    @Override
    public MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId) {
        LeagueEntity league = findLeague(leagueId);
        return switch (league.getMatchType()) {
            case SINGLES -> new FreeSinglesMatchStrategy(singlesMatchReader, singlesMatchStore);
            case DOUBLES -> new FreeDoublesMatchStrategy(doublesMatchReader, doublesMatchStore);
        };
    }

    @Override
    public BracketInfo retrieveBracket(MatchStrategy matchStrategy, Long leagueId) {
        return matchStrategy.retrieveFreeBracketInLeague(leagueId);
    }

    @Override
    public List<SetInfo.Main> retrieveAllSetsScoreInBracket(MatchStrategy matchStrategy, Long leagueId) {
        return matchStrategy.retrieveAllSetsScoreInLeague(leagueId);
    }

    @Override
    public SetScoreDetails retrieveAllSetsScoreInMatch(MatchStrategy matchStrategy, Long matchId) {
        return matchStrategy.retrieveAllSetsScoreInMatch(matchId);
    }

    private LeagueEntity findLeague(Long leagueId) {
        return leagueRepository.findById(leagueId)
                .orElseThrow(() -> new LeagueNotExistException(leagueId));
    }
}
