package org.badminton.domain.domain.match.service;

import java.time.LocalDateTime;
import java.util.List;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipantEntity;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.MatchInfo.SetScoreDetails;
import org.badminton.domain.domain.match.info.SetInfo;

/*
 * 구현체
 * 1. FreeSinglesMatchStrategy
 * 2. FreeDoublesMatchStrategy
 * 3. TournamentSinglesMatchStrategy
 * 4. TournamentSinglesMatchStrategy
 */
public interface MatchStrategy {

    BracketInfo retrieveFreeBracketInLeague(Long leagueId);

    List<SetInfo.Main> retrieveAllSetsScoreInLeague(Long leagueId);

    SetScoreDetails retrieveAllSetsScoreInMatch(Long matchId);

    void checkDuplicateInitialBracket(LocalDateTime leagueAt, Long leagueId);

    BracketInfo makeInitialBracket(League league, List<LeagueParticipantEntity> leagueParticipantList);

    SetInfo.Main registerSetScoreInMatch(Long matchId, int setIndex, MatchCommand.UpdateSetScore updateSetScoreCommand);
}
