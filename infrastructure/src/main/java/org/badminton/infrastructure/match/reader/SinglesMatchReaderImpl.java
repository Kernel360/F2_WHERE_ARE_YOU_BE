package org.badminton.infrastructure.match.reader;

import java.util.List;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.match.BracketNotExistException;
import org.badminton.domain.common.exception.match.MatchNotExistException;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.badminton.infrastructure.match.repository.SinglesMatchRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SinglesMatchReaderImpl implements SinglesMatchReader {
	private final SinglesMatchRepository singlesMatchRepository;

	@Override
	public List<SinglesMatch> getSinglesBracket(Long leagueId) {
		List<SinglesMatch> bracketInLeague = singlesMatchRepository.findAllByLeague_LeagueId(leagueId);
		if (bracketInLeague.isEmpty()) {
			throw new BracketNotExistException(leagueId);
		}
		return bracketInLeague;
	}

	@Override
	public boolean checkIfBracketEmpty(Long leagueId) {
		List<SinglesMatch> bracketInLeague = singlesMatchRepository.findAllByLeague_LeagueId(leagueId);
		return bracketInLeague.isEmpty();
	}

	@Override
	public SinglesMatch getSinglesMatch(Long matchId) {
		return singlesMatchRepository.findById(matchId)
			.orElseThrow(() -> new MatchNotExistException(matchId, MatchType.SINGLES));
	}

	@Override
	public List<SinglesMatch> getSinglesMatchesByClubMember(Long clubMemberId) {
		return singlesMatchRepository.findAllCompletedByClubMemberId(clubMemberId);
	}
}