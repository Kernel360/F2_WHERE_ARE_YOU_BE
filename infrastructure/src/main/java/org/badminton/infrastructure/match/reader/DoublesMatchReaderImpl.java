package org.badminton.infrastructure.match.reader;

import java.util.List;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.match.BracketNotExistException;
import org.badminton.domain.common.exception.match.MatchNotExistException;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.reader.DoublesMatchRepositoryCustom;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.infrastructure.match.repository.DoublesMatchRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DoublesMatchReaderImpl implements DoublesMatchReader {

	private final DoublesMatchRepository doublesMatchRepository;
	private final DoublesMatchRepositoryCustom doublesMatchRepositoryCustom;

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
		return bracketInLeague.isEmpty();
	}

	@Override
	public DoublesMatch getDoublesMatch(Long matchId) {
		return doublesMatchRepository.findById(matchId).orElseThrow(() ->
			new MatchNotExistException(matchId, MatchType.DOUBLES));
	}

	@Override
	public List<DoublesMatch> findAllCompletedByClubMemberId(Long clubMemberId) {
		return doublesMatchRepositoryCustom.findAllCompletedByClubMemberId(clubMemberId);
	}

	@Override
	public List<DoublesMatch> findMatchesByLeagueAndRound(Long leagueId, Integer currentRoundNumber) {
		return doublesMatchRepository.findAllByLeague_LeagueIdAndRoundNumber(leagueId, currentRoundNumber);
	}

	@Override
	public DoublesMatch findFirstMatchByLeagueId(Long leagueId) {
		return doublesMatchRepository.findFirstByLeagueLeagueIdOrderByIdAsc(leagueId);
	}

	@Override
	public boolean allMatchesFinishedForLeague(Long leagueId) {
		return doublesMatchRepository.allMatchesFinishedForLeague(leagueId);
	}

	@Override
	public boolean allMatchesNotStartedForLeague(Long leagueId) {
		return doublesMatchRepository.allMatchesNotStartedForLeague(leagueId);
	}
}
