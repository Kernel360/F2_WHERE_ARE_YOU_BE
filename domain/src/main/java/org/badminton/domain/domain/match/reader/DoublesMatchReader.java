package org.badminton.domain.domain.match.reader;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.entity.DoublesMatch;

public interface DoublesMatchReader {
	List<DoublesMatch> getDoublesBracket(Long leagueId);

	boolean checkIfBracketEmpty(Long leagueId);

	DoublesMatch getDoublesMatch(Long matchId);

	List<DoublesMatch> findAllCompletedByClubMemberId(Long clubMemberId);

	List<DoublesMatch> findMatchesByLeagueAndRound(Long leagueId, Integer round);

	DoublesMatch findFirstMatchByLeagueId(Long leagueId);

	boolean allMatchesFinishedForLeague(Long leagueId);

	boolean allMatchesNotStartedForLeague(Long leagueId);

	boolean allRoundMatchesDone(Long leagueId, int roundNumber);

	Optional<DoublesMatch> findMatchByLeagueParticipant(LeagueParticipant leagueParticipant);

}
