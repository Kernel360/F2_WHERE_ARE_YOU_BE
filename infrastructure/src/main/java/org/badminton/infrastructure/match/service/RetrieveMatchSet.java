package org.badminton.infrastructure.match.service;

import java.util.Optional;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.common.exception.match.LeagueParticipantNotDeterminedException;
import org.badminton.domain.common.exception.match.PreviousDetNotFinishedException;
import org.badminton.domain.common.exception.match.RoundNotFinishedException;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.entity.SinglesSet;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.reader.DoublesMatchStore;
import org.badminton.domain.domain.match.reader.SinglesMatchStore;
import org.badminton.domain.domain.match.store.DoublesMatchReader;
import org.badminton.domain.domain.match.store.SinglesMatchReader;
import org.badminton.domain.domain.match.vo.RedisKey;
import org.badminton.domain.domain.match.vo.Score;
import org.badminton.infrastructure.match.repository.SetRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RetrieveMatchSet {
	private final SetRepository setRepository;
	private final LeagueReader leagueReader;
	private final SinglesMatchReader singlesMatchReader;
	private final DoublesMatchReader doublesMatchReader;
	private final SinglesMatchStore singlesMatchStore;
	private final DoublesMatchStore doublesMatchStore;

	public void setMatchSetScore(Long leagueId, Long matchId, int setNumber, Score score, String memberToken) {
		League league = leagueReader.readLeagueById(leagueId);

		LeagueSetsScoreInProgressInfo leagueSetsScoreInProgressInfo = null;
		if (league.getMatchType() == MatchType.SINGLES) {
			validatePreviousSinglesRoundCompletion(leagueId, matchId);
			validateSinglesMatch(matchId, setNumber);
			SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
			singlesMatch.startMatchSet(setNumber);
			singlesMatch.getSinglesSet(setNumber).saveSetScore(score.getLeft(), score.getRight());
			leagueSetsScoreInProgressInfo = LeagueSetsScoreInProgressInfo.fromSinglesMatchAndSet(singlesMatch,
				singlesMatch.getSinglesSet(setNumber));
		} else if (league.getMatchType() == MatchType.DOUBLES) {
			validatePreviousDoublesRoundCompletion(leagueId, matchId);
			validateDoublesMatch(matchId, setNumber);
			DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
			doublesMatch.startMatchSet(setNumber);
			doublesMatch.getDoublesSet(setNumber).saveSetScore(score.getLeft(), score.getRight());
			leagueSetsScoreInProgressInfo = LeagueSetsScoreInProgressInfo.fromDoublesMatchAndSet(doublesMatch,
				doublesMatch.getDoublesSet(setNumber));
		}
		setRepository.saveInProgressSet(leagueId, matchId, setNumber, leagueSetsScoreInProgressInfo);
		setRepository.setMatchSetScore(league.getMatchType(), matchId, setNumber, score);
	}

	private void validatePreviousSinglesRoundCompletion(Long leagueId, Long matchId) {
		SinglesMatch match = singlesMatchReader.getSinglesMatch(matchId);
		int roundNumber = match.getRoundNumber();

		if (roundNumber == 1) {
			return;
		}

		if (!singlesMatchReader.allRoundMatchesDone(leagueId, roundNumber - 1)) {
			throw new RoundNotFinishedException(roundNumber - 1);
		}
	}

	private void validatePreviousDoublesRoundCompletion(Long leagueId, Long matchId) {
		DoublesMatch match = doublesMatchReader.getDoublesMatch(matchId);
		int roundNumber = match.getRoundNumber();

		if (roundNumber == 1) {
			return;
		}

		if (!doublesMatchReader.allRoundMatchesDone(leagueId, roundNumber - 1)) {
			throw new RoundNotFinishedException(roundNumber - 1);
		}
	}

	private void validateSinglesMatch(Long matchId, int setNumber) {
		SinglesMatch match = singlesMatchReader.getSinglesMatch(matchId);

		if (match.getLeagueParticipant1() == null || match.getLeagueParticipant2() == null) {
			throw new LeagueParticipantNotDeterminedException(matchId);
		}

		if (setNumber == 1) {
			return;
		}

		if (match.getSinglesSet(setNumber - 1).getSetStatus() != SetStatus.FINISHED) {
			throw new PreviousDetNotFinishedException(setNumber - 1);
		}
	}

	private void validateDoublesMatch(Long matchId, int setNumber) {
		DoublesMatch match = doublesMatchReader.getDoublesMatch(matchId);

		if (match.getTeam1() == null || match.getTeam2() == null) {
			throw new LeagueParticipantNotDeterminedException(matchId);
		}

		if (setNumber == 1) {
			return;
		}

		if (match.getDoublesSet(setNumber - 1).getSetStatus() != SetStatus.FINISHED) {
			throw new PreviousDetNotFinishedException(setNumber - 1);
		}
	}

	@Transactional
	public void registerMatchSetScoreInDb(RedisKey key, Score score) {
		if (key.getMatchType() == MatchType.SINGLES) {
			SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(key.getMatchId());
			SinglesSet singlesSet = singlesMatch.getSinglesSet(key.getSetNumber());
			singlesSet.saveSetScore(score.getLeft(), score.getRight());
			singlesMatchStore.store(singlesMatch);
		} else if (key.getMatchType() == MatchType.DOUBLES) {
			DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(key.getMatchId());
			DoublesSet doublesSet = doublesMatch.getDoublesSet(key.getSetNumber());
			doublesSet.saveSetScore(score.getLeft(), score.getRight());
			doublesMatchStore.store(doublesMatch);
		}
	}

	public Optional<Score> getMatchSetScore(Long leagueId, Long matchId, int setNumber) {
		League league = leagueReader.readLeagueById(leagueId);
		return setRepository.getMatchSetScore(league.getMatchType(), matchId, setNumber);
	}

	public MatchType getMatchType(Long leagueId) {
		League league = leagueReader.readLeagueById(leagueId);
		return league.getMatchType();
	}

	public void deleteCache(RedisKey key) {
		setRepository.deleteScore(key);
	}
}
