package org.badminton.infrastructure.match.service;

import lombok.RequiredArgsConstructor;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.league.LeagueParticipationNotExistException;
import org.badminton.domain.domain.league.LeagueParticipantReader;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.entity.SinglesSet;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.badminton.domain.domain.match.vo.Score;
import org.badminton.infrastructure.match.repository.SetRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RetrieveMatchSet {
    private final SetRepository setRepository;
    private final LeagueReader leagueReader;
    private final LeagueParticipantReader leagueParticipantReader;
    // TODO: db에 저장
    private final SinglesMatchReader singlesMatchReader;
    private final DoublesMatchReader doublesMatchReader;
    private final SinglesMatchStore singlesMatchStore;
    private final DoublesMatchStore doublesMatchStore;

    public void setMatchSetScore(Long leagueId, Long matchId, int setNumber, Score score, String memberToken) {
        if (!leagueParticipantReader.isParticipant(memberToken, leagueId)) {
            throw new LeagueParticipationNotExistException(leagueId, memberToken);
        }
        League league = leagueReader.readLeagueById(leagueId);
        setRepository.setMatchSetScore(league.getMatchType(), matchId, setNumber, score);

        // TODO: DB에 저장
        if (getMatchType(leagueId) == MatchType.SINGLES) {
            SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
            SinglesSet singlesSet = singlesMatch.getSinglesSet(setNumber);
            singlesSet.saveSetScore(score.getLeft(), score.getRight());
            singlesMatchStore.store(singlesMatch);
        } else if (getMatchType(leagueId) == MatchType.DOUBLES) {
            DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
            DoublesSet doublesSet = doublesMatch.getDoublesSet(setNumber);
            doublesSet.saveSetScore(score.getLeft(), score.getRight());
            doublesMatchStore.store(doublesMatch);
        }
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
