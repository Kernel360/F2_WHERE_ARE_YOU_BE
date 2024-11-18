package org.badminton.infrastructure.match.strategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.SetFinishedException;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.vo.Team;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.service.AbstractDoublesMatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FreeDoublesMatchStrategy extends AbstractDoublesMatchStrategy {

    private final DoublesMatchReader doublesMatchReader;
    private final DoublesMatchStore doublesMatchStore;

    public FreeDoublesMatchStrategy(DoublesMatchReader doublesMatchReader, DoublesMatchStore doublesMatchStore) {
        super(doublesMatchReader);
        this.doublesMatchReader = doublesMatchReader;
        this.doublesMatchStore = doublesMatchStore;
    }

    @Override
    public BracketInfo makeBracket(League league,
                                   List<LeagueParticipant> leagueParticipantList) {
        Collections.shuffle(leagueParticipantList);
        List<DoublesMatch> doublesMatches = makeDoublesMatches(leagueParticipantList, league, 1);
        doublesMatches.forEach(this::makeDoublesSetsInMatch);
        return BracketInfo.fromDoubles(1, doublesMatches);
    }

    @Override
    public SetInfo.Main registerSetScoreInMatch(Long matchId, int setNumber,
                                                MatchCommand.UpdateSetScore updateSetScoreCommand) {
        DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);

        if (doublesMatch.getDoublesSet(setNumber).getSetStatus() == SetStatus.FINISHED) {
            throw new SetFinishedException(setNumber);
        }

        doublesMatch.getDoublesSet(setNumber)
                .endSetScore(updateSetScoreCommand.getScore1(), updateSetScoreCommand.getScore2());

        if (updateSetScoreCommand.getScore1() > updateSetScoreCommand.getScore2()) {
            doublesMatch.team1WinSet();
        } else {
            doublesMatch.team2WinSet();
        }

        doublesMatchStore.store(doublesMatch);
        return SetInfo.fromDoublesSet(matchId, setNumber, doublesMatch.getDoublesSets().get(setNumber - 1));
    }

    @Override
    public SetInfo.Main retrieveSet(Long matchId, int setNumber) {
        DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
        DoublesSet doublesSet = doublesMatch.getDoublesSet(setNumber);
        return SetInfo.fromDoublesSet(matchId, setNumber, doublesSet);
    }

    @Override
    public void checkDuplicateInitialBracket(LocalDateTime leagueAt, Long leagueId) {
        boolean isBracketEmpty = doublesMatchReader.checkIfBracketEmpty(leagueId);
        if (!isBracketEmpty && LocalDateTime.now().isBefore(leagueAt)) {
            doublesMatchStore.deleteDoublesBracket(leagueId);
        } else if (!isBracketEmpty && LocalDateTime.now().isAfter(leagueAt)) {
            // TODO: 경기 시작 시간이 지났는데, 이미 대진표가 있는데 또 생성하고 싶다면? -> 막을지, 안막을지
        }
    }

    private void makeDoublesSetsInMatch(DoublesMatch doublesMatch) {
        // 복식 게임 세트를 3개 생성
        DoublesSet set1 = new DoublesSet(doublesMatch, 1);
        DoublesSet set2 = new DoublesSet(doublesMatch, 2);
        DoublesSet set3 = new DoublesSet(doublesMatch, 3);

        // 복식 게임 결과를 생성
        doublesMatch.addSet(set1);
        doublesMatch.addSet(set2);
        doublesMatch.addSet(set3);

        doublesMatchStore.store(doublesMatch);
    }

    private List<DoublesMatch> makeDoublesMatches(List<LeagueParticipant> leagueParticipantList,
                                                  League league, int roundNumber) {

        List<DoublesMatch> doublesMatches = new ArrayList<>();
        for (int i = 0; i < leagueParticipantList.size() - 3; i += 4) {
            Team team1 = new Team(leagueParticipantList.get(i), leagueParticipantList.get(i + 1));
            Team team2 = new Team(leagueParticipantList.get(i + 2), leagueParticipantList.get(i + 3));
            DoublesMatch doublesMatch = new DoublesMatch(league, team1, team2, roundNumber);
            doublesMatches.add(doublesMatch);
            doublesMatchStore.store(doublesMatch);
        }
        return doublesMatches;
    }
}