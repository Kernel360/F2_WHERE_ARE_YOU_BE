package org.badminton.infrastructure.match.service;

import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.match.info.SetInfo.Main;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.service.AbstractMatchRetrieveService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.badminton.infrastructure.match.strategy.FreeDoublesMatchStrategy;
import org.badminton.infrastructure.match.strategy.FreeSinglesMatchStrategy;
import org.springframework.stereotype.Service;

@Service
public class FreeMatchRetrieveServiceImpl extends AbstractMatchRetrieveService {

    private final LeagueReader leagueReader;
    private final SinglesMatchReader singlesMatchReader;
    private final DoublesMatchReader doublesMatchReader;
    private final SinglesMatchStore singlesMatchStore;
    private final DoublesMatchStore doublesMatchStore;

    public FreeMatchRetrieveServiceImpl(LeagueReader leagueReader, SinglesMatchReader singlesMatchReader,
                                        DoublesMatchReader doublesMatchReader,
                                        SinglesMatchStore singlesMatchStore, DoublesMatchStore doublesMatchStore) {
        this.singlesMatchReader = singlesMatchReader;
        this.doublesMatchReader = doublesMatchReader;
        this.singlesMatchStore = singlesMatchStore;
        this.doublesMatchStore = doublesMatchStore;
        this.leagueReader = leagueReader;
    }

    @Override
    public MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId) {
        League league = findLeague(leagueId);
        return switch (league.getMatchType()) {
            case SINGLES -> new FreeSinglesMatchStrategy(singlesMatchReader, singlesMatchStore);
            case DOUBLES -> new FreeDoublesMatchStrategy(doublesMatchReader, doublesMatchStore);
        };
    }

    @Override
    public Main retrieveSet(MatchStrategy matchStrategy, Long matchId, int setNumber) {
        return matchStrategy.retrieveSet(matchId, setNumber);
    }

    private League findLeague(Long leagueId) {
        return leagueReader.readLeagueById(leagueId);
    }
}
