package org.badminton.domain.domain.match.store;

import java.util.List;

import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.springframework.data.repository.query.Param;

public interface DoublesMatchReader {
	List<DoublesMatch> getDoublesBracket(Long leagueId);

	boolean checkIfBracketEmpty(Long leagueId);

	DoublesMatch getDoublesMatch(Long matchId);

	List<DoublesMatch> findAllCompletedByClubMemberId(@Param("clubMemberId") Long clubMemberId);
}
