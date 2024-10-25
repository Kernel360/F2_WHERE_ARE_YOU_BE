package org.badminton.domain.infrastructures.match.service;

import lombok.RequiredArgsConstructor;
import org.badminton.domain.common.exception.league.LeagueNotExistException;
import org.badminton.domain.domain.league.entity.LeagueEntity;
import org.badminton.domain.domain.match.command.MatchCommand.UpdateSetScore;
import org.badminton.domain.domain.match.info.SetInfo.Main;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.service.MatchProgressService;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.badminton.domain.infrastructures.league.LeagueRepository;
import org.badminton.domain.infrastructures.match.strategy.FreeDoublesMatchStrategy;
import org.badminton.domain.infrastructures.match.strategy.FreeSinglesMatchStrategy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchProgressServiceImpl implements MatchProgressService {

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
    public Main registerSetScoreInMatch(MatchStrategy matchStrategy, Long leagueId, Long matchId, int setIndex,
                                        UpdateSetScore updateSetScoreCommand) {
        return matchStrategy.registerSetScoreInMatch(matchId, setIndex, updateSetScoreCommand);
    }

    private LeagueEntity findLeague(Long leagueId) {
        return leagueRepository.findById(leagueId)
                .orElseThrow(() -> new LeagueNotExistException(leagueId));
    }
}
