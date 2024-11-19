package org.badminton.infrastructure.match.service;

import lombok.RequiredArgsConstructor;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.league.LeagueParticipationNotExistException;
import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.match.vo.Score;
import org.badminton.infrastructure.match.repository.SetRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RetrieveMatchSet {
    private final SetRepository setRepository;
    private final LeagueReader leagueReader;
    private final LeagueParticipantReader leagueParticipantReader;

    public void setMatchSetScore(Long leagueId, Long matchId, int setNumber, Score score, String memberToken) {
        if (!leagueParticipantReader.isParticipant(memberToken, leagueId)) {
            throw new LeagueParticipationNotExistException(leagueId, memberToken);
        }
        League league = leagueReader.readLeagueById(leagueId);
        setRepository.setMatchSetScore(league.getMatchType(), matchId, setNumber, score);
    }

    public Score getMatchSetScore(Long leagueId, Long matchId, int setNumber) {
        League league = leagueReader.readLeagueById(leagueId);
        return setRepository.getMatchSetScore(league.getMatchType(), matchId, setNumber);
    }

    public MatchType getMatchType(Long leagueId) {
        League league = leagueReader.readLeagueById(leagueId);
        return league.getMatchType();
    }
}
