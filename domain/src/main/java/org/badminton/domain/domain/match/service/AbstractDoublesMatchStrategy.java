package org.badminton.domain.domain.match.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class AbstractDoublesMatchStrategy implements MatchStrategy {

    public static final int FIRST_ROUND_NUMBER = 1;
    private final DoublesMatchReader doublesMatchReader;

    @Override
    public abstract void checkDuplicateInitialBracket(Long leagueId);

    @Override
    public BracketInfo retrieveBracketInLeague(Long leagueId) {
        List<DoublesMatch> bracketInLeague = doublesMatchReader.getDoublesBracket(leagueId);
        return BracketInfo.fromDoubles(FIRST_ROUND_NUMBER, bracketInLeague);
    }

    @Override
    public List<SetInfo.Main> retrieveAllSetsScoreInLeague(Long leagueId) {
        return doublesMatchReader.getDoublesBracket(leagueId).stream()
                .flatMap(doublesMatch ->
                        doublesMatch.getDoublesSets().stream()
                                .map(doublesSet -> SetInfo.fromDoublesSet(doublesMatch.getId(),
                                        doublesSet.getSetNumber(), doublesSet))
                )
                .toList();
    }

    @Override
    public MatchInfo.SetScoreDetails retrieveAllSetsScoreInMatch(Long matchId) {
        DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
        return MatchInfo.SetScoreDetails.fromDoublesMatchToMatchDetails(doublesMatch);
    }

    @Override
    public abstract BracketInfo makeBracket(League league, List<LeagueParticipant> leagueParticipantList);

    @Override
    public SetInfo.Main registerSetScoreInMatch(Long matchId, Integer setIndex,
                                                MatchCommand.UpdateSetScore updateSetScoreCommand) {
        return null;
    }

    @Override
    public boolean isMatchInLeague(Long leagueId) {
        return !doublesMatchReader.checkIfBracketEmpty(leagueId);
    }

    @Override
    public List<LeagueSetsScoreInProgressInfo> retrieveLeagueSetsScoreInProgress(Long leagueId) {
        List<DoublesMatch> doublesMatchInProgress = doublesMatchReader.getDoublesBracket(leagueId).stream()
                .filter(doublesMatch -> doublesMatch.getMatchStatus() == MatchStatus.IN_PROGRESS)
                .sorted(Comparator.comparing(DoublesMatch::getId))
                .toList();

        List<DoublesSet> doublesSetInProgress = doublesMatchInProgress.stream()
                .filter(doublesMatch -> doublesMatch.getSetInProgress().isPresent())
                .map(doublesMatch -> doublesMatch.getSetInProgress().get())
                .toList();

        if (doublesSetInProgress.size() != doublesMatchInProgress.size()) {
            throw new BadmintonException(ErrorCode.INVALID_PARAMETER);
        }

        List<LeagueSetsScoreInProgressInfo> leagueSetsScoreInProgressInfo = new ArrayList<>();

        for (int i = 0; i < doublesSetInProgress.size(); i++) {
            leagueSetsScoreInProgressInfo.add(LeagueSetsScoreInProgressInfo.fromDoublesMatchAndSet(
                    doublesMatchInProgress.get(i), doublesSetInProgress.get(i)
            ));
        }
        return leagueSetsScoreInProgressInfo;
    }

    @Override
    public MatchSetInfo retrieveMatchSet(Long matchId, int setNumber) {
        DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
        DoublesSet doublesSet = doublesMatch.getDoublesSet(setNumber);
        return MatchSetInfo.fromDoubles(doublesSet);
    }
}
