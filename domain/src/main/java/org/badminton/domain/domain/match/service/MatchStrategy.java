package org.badminton.domain.domain.match.service;

import java.util.List;

import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.info.MatchInfo.SetScoreDetails;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;

/*
 * 구현체
 * 1. FreeSinglesMatchStrategy
 * 2. FreeDoublesMatchStrategy
 * 3. TournamentSinglesMatchStrategy
 * 4. TournamentSinglesMatchStrategy
 */
public interface MatchStrategy {

	BracketInfo retrieveBracketInLeague(Long leagueId);

	List<SetInfo.Main> retrieveAllSetsScoreInLeague(Long leagueId);

	SetScoreDetails retrieveAllSetsScoreInMatch(Long matchId);

	void checkDuplicateInitialBracket(Long leagueId);

	BracketInfo makeBracket(League league, List<LeagueParticipant> leagueParticipantList);

	SetInfo.Main registerSetScoreInMatch(Long matchId, int setIndex, MatchCommand.UpdateSetScore updateSetScoreCommand);

	boolean isMatchInLeague(Long leagueId);

	List<LeagueSetsScoreInProgressInfo> retrieveLeagueSetsScoreInProgress(Long leagueId);

	MatchSetInfo retrieveMatchSet(Long matchId, int setNumber);

	SetInfo.Main retrieveSet(Long matchId, int setNumber);

	void initMatch(Long matchId);

}
