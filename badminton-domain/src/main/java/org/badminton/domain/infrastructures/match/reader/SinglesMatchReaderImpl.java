package org.badminton.domain.infrastructures.match.reader;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.match.BracketNotExistException;
import org.badminton.domain.common.exception.match.MatchNotExistException;
import org.badminton.domain.domain.match.entity.SinglesMatchEntity;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.badminton.domain.infrastructures.match.repository.SinglesMatchRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SinglesMatchReaderImpl implements SinglesMatchReader {
    private final SinglesMatchRepository singlesMatchRepository;

    @Override
    public List<SinglesMatchEntity> getSinglesBracket(Long leagueId) {
        List<SinglesMatchEntity> bracketInLeague = singlesMatchRepository.findAllByLeague_LeagueId(leagueId);
        if (bracketInLeague.isEmpty()) {
            throw new BracketNotExistException(leagueId);
        }
        return bracketInLeague;
    }

    @Override
    public boolean checkIfBracketEmpty(Long leagueId) {
        List<SinglesMatchEntity> bracketInLeague = singlesMatchRepository.findAllByLeague_LeagueId(leagueId);
        return bracketInLeague.isEmpty();
    }

    @Override
    public SinglesMatchEntity getSinglesMatch(Long matchId) {
        return singlesMatchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotExistException(matchId, MatchType.SINGLES));
    }
}
