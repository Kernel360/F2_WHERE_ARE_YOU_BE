package org.badminton.infrastructure.match.reader;

import java.util.List;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.match.BracketNotExistException;
import org.badminton.domain.common.exception.match.MatchNotExistException;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.infrastructure.match.repository.DoublesMatchRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DoublesMatchReaderImpl implements DoublesMatchReader {

	private final DoublesMatchRepository doublesMatchRepository;

	@Override
	public List<DoublesMatch> getDoublesBracket(Long leagueId) {
		List<DoublesMatch> bracketInLeague = doublesMatchRepository.findAllByLeague_LeagueId(leagueId);
		if (bracketInLeague.isEmpty()) {
			throw new BracketNotExistException(leagueId);
		}
		return bracketInLeague;
	}

	@Override
	public boolean checkIfBracketEmpty(Long leagueId) {
		List<DoublesMatch> bracketInLeague = doublesMatchRepository.findAllByLeague_LeagueId(leagueId);
		if (bracketInLeague.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public DoublesMatch getDoublesMatch(Long matchId) {
		return doublesMatchRepository.findById(matchId).orElseThrow(() ->
			new MatchNotExistException(matchId, MatchType.DOUBLES));
	}

	@Override
	public List<DoublesMatch> findAllCompletedByClubMemberId(Long clubMemberId) {
		return doublesMatchRepository.findAllCompletedByClubMemberId(clubMemberId);
	}
}
