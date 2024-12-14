package org.badminton.infrastructure.match.reader;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.match.BracketNotExistException;
import org.badminton.domain.common.exception.match.MatchNotExistException;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.reader.DoublesMatchReader;
import org.badminton.domain.domain.match.reader.DoublesMatchRepositoryCustom;
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
	public DoublesMatch readDoublesMatch(Long leagueId, Long matchId) {
		return doublesMatchRepository.findByIdAndLeagueLeagueId(matchId, leagueId);
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
		List<MatchStatus> UnPlayedMatchStatuses = Arrays.asList(MatchStatus.NOT_STARTED, MatchStatus.BYE);
		return doublesMatchRepository.allMatchesNotStartedForLeague(leagueId, UnPlayedMatchStatuses);
	}

	@Override
	public boolean allRoundMatchesDone(Long leagueId, int roundNumber) {
		List<MatchStatus> CompletedOrBypassedStatuses = List.of(MatchStatus.FINISHED, MatchStatus.BYE);
		return doublesMatchRepository.areAllMatchesFinishedOrBye(leagueId, roundNumber, CompletedOrBypassedStatuses);
	}

	@Override
	public Optional<DoublesMatch> findMatchByLeagueParticipant(LeagueParticipant leagueParticipant) {
		return doublesMatchRepository.findByLeagueParticipant(leagueParticipant);
	}
}
