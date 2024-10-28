package org.badminton.domain.infrastructures.match.store;

import lombok.RequiredArgsConstructor;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.infrastructures.match.repository.SinglesMatchRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SinglesMatchStoreImpl implements SinglesMatchStore {
    private final SinglesMatchRepository singlesMatchRepository;

    @Override
    public void deleteSinglesBracket(Long leagueId) {
        singlesMatchRepository.deleteAllByLeague_LeagueId(leagueId);
    }

    @Override
    public void store(SinglesMatch singlesMatch) {
        singlesMatchRepository.save(singlesMatch);
    }
}
