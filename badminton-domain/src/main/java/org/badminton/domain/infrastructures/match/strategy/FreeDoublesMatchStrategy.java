package org.badminton.domain.infrastructures.match.strategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipantEntity;
import org.badminton.domain.domain.league.vo.Team;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSetEntity;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.service.MatchStrategy;
import org.badminton.domain.domain.match.store.DoublesMatchReader;

@AllArgsConstructor
public class FreeDoublesMatchStrategy implements MatchStrategy {

    private final DoublesMatchReader doublesMatchReader;
    private final DoublesMatchStore doublesMatchStore;

    @Override
    public BracketInfo retrieveFreeBracketInLeague(Long leagueId) {
        List<DoublesMatch> bracketInLeague = doublesMatchReader.getDoublesBracket(leagueId);
        return BracketInfo.fromDoubles(1, bracketInLeague);
    }

    @Override
    public List<SetInfo.Main> retrieveAllSetsScoreInLeague(Long leagueId) {
        return doublesMatchReader.getDoublesBracket(leagueId).stream()
                .flatMap(doublesMatch ->
                        doublesMatch.getDoublesSets().stream()
                                .map(doublesSet -> SetInfo.fromDoublesSet(doublesMatch.getId(),
                                        doublesSet.getSetIndex(), doublesSet))
                )
                .toList();
    }

    @Override
    public MatchInfo.SetScoreDetails retrieveAllSetsScoreInMatch(Long matchId) {
        DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
        return MatchInfo.SetScoreDetails.fromDoublesMatchToMatchDetails(doublesMatch);
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

    @Override
    public BracketInfo makeInitialBracket(League league,
                                          List<LeagueParticipantEntity> leagueParticipantList) {
        Collections.shuffle(leagueParticipantList);
        List<DoublesMatch> doublesMatches = makeDoublesMatches(leagueParticipantList, league);
        doublesMatches.forEach(this::makeDoublesSetsInMatch);
        return BracketInfo.fromDoubles(1, doublesMatches);
    }

    @Override
    public SetInfo.Main registerSetScoreInMatch(Long matchId, int setIndex,
                                                MatchCommand.UpdateSetScore updateSetScoreCommand) {
        DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
        // 세트 스코어를 기록한다.
        doublesMatch.getDoublesSets()
                .get(setIndex - 1)
                .saveSetScore(updateSetScoreCommand.getScore1(), updateSetScoreCommand.getScore2());

        // 승자에 따라 Match에 이긴 세트수를 업데이트해준다. 만약 2번을 모두 이긴 팀이 있다면 해당 Match는 종료된다.
        if (updateSetScoreCommand.getScore1() > updateSetScoreCommand.getScore2()) {
            doublesMatch.team1WinSet();
        } else {
            doublesMatch.team2WinSet();
        }

        doublesMatchStore.store(doublesMatch);
        return SetInfo.fromDoublesSet(matchId, setIndex, doublesMatch.getDoublesSets().get(setIndex - 1));
    }

    @Override
    public boolean isMatchInLeague(Long leagueId) {
        return !doublesMatchReader.checkIfBracketEmpty(leagueId);
    }

    private void makeDoublesSetsInMatch(DoublesMatch doublesMatch) {
        // 복식 게임 세트를 3개 생성
        DoublesSetEntity set1 = new DoublesSetEntity(doublesMatch, 1);
        DoublesSetEntity set2 = new DoublesSetEntity(doublesMatch, 2);
        DoublesSetEntity set3 = new DoublesSetEntity(doublesMatch, 3);

        // 복식 게임 결과를 생성
        doublesMatch.addSet(set1);
        doublesMatch.addSet(set2);
        doublesMatch.addSet(set3);

        doublesMatchStore.store(doublesMatch);
    }

    private List<DoublesMatch> makeDoublesMatches(List<LeagueParticipantEntity> leagueParticipantList,
                                                  League league) {

        List<DoublesMatch> doublesMatches = new ArrayList<>();
        for (int i = 0; i < leagueParticipantList.size() - 3; i += 4) {
            Team team1 = new Team(leagueParticipantList.get(i), leagueParticipantList.get(i + 1));
            Team team2 = new Team(leagueParticipantList.get(i + 2), leagueParticipantList.get(i + 3));
            DoublesMatch doublesMatch = new DoublesMatch(league, team1, team2);
            doublesMatches.add(doublesMatch);
            doublesMatchStore.store(doublesMatch);
        }
        return doublesMatches;
    }
}