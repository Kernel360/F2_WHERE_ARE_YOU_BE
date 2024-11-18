package org.badminton.infrastructure.match.service;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.infrastructure.match.repository.SetRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveMatchSet {
    private final SetRepository setRepository;
    private final LeagueReader leagueReader;

    public void setMatchSetScore(Long leagueId, Long matchId, int setNumber, List<Integer> setScore) {
        League league = leagueReader.readLeagueById(leagueId);
        setRepository.setMatchSetScore(league.getMatchType(), matchId, setNumber, setScore);
    }

    public List<Integer> getMatchSetScore(Long leagueId, Long matchId, int setNumber) {
        League league = leagueReader.readLeagueById(leagueId);
        String score = setRepository.getMatchSetScore(league.getMatchType(), matchId, setNumber);
        return Arrays.stream(score.split(":"))
                .map(Integer::parseInt)
                .toList();
    }

    public MatchType getMatchType(Long leagueId) {
        League league = leagueReader.readLeagueById(leagueId);
        return league.getMatchType();
    }
}
