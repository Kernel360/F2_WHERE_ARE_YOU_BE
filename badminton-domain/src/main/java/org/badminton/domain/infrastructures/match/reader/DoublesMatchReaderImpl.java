package org.badminton.domain.infrastructures.match.reader;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.match.BracketNotExistException;
import org.badminton.domain.common.exception.match.MatchNotExistException;
import org.badminton.domain.domain.match.entity.DoublesMatchEntity;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.infrastructures.match.repository.DoublesMatchRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoublesMatchReaderImpl implements DoublesMatchReader {

    private final DoublesMatchRepository doublesMatchRepository;

    @Override
    public List<DoublesMatchEntity> getDoublesBracket(Long leagueId) {
        List<DoublesMatchEntity> bracketInLeague = doublesMatchRepository.findAllByLeague_LeagueId(leagueId);
        if (bracketInLeague.isEmpty()) {
            throw new BracketNotExistException(leagueId);
        }
        return bracketInLeague;
    }

    @Override
    public boolean checkIfBracketEmpty(Long leagueId) {
        List<DoublesMatchEntity> bracketInLeague = doublesMatchRepository.findAllByLeague_LeagueId(leagueId);
        if (bracketInLeague.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public DoublesMatchEntity getDoublesMatch(Long matchId) {
        return doublesMatchRepository.findById(matchId).orElseThrow(() ->
                new MatchNotExistException(matchId, MatchType.DOUBLES));
    }
}
