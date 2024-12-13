package org.badminton.domain.domain.match.reader;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.entity.SinglesMatch;

public interface SinglesMatchReader {
	List<SinglesMatch> getSinglesBracket(Long leagueId);

	boolean checkIfBracketEmpty(Long leagueId);

	SinglesMatch getSinglesMatch(Long matchId);

	List<SinglesMatch> getSinglesMatchesByClubMember(Long clubMemberId);

	List<SinglesMatch> findMatchesByLeagueAndRound(Long leagueId, int currentRoundNumber);

	int findMaxRoundByLeague(Long leagueId);

	SinglesMatch findFirstMatchByLeagueId(Long leagueId);

	boolean allMatchesFinishedForLeague(Long leagueId);

	boolean allMatchesNotStartedForLeague(Long leagueId);

	boolean allRoundMatchesDone(Long leagueId, int roundNumber);

	Optional<SinglesMatch> findMatchByLeagueParticipant(LeagueParticipant leagueParticipant);

}
