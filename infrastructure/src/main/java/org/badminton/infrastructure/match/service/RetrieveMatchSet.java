package org.badminton.infrastructure.match.service;

import java.util.Optional;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.LeagueReader;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.match.entity.DoublesMatch;
import org.badminton.domain.domain.match.entity.DoublesSet;
import org.badminton.domain.domain.match.entity.SinglesMatch;
import org.badminton.domain.domain.match.entity.SinglesSet;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.reader.DoublesMatchReader;
import org.badminton.domain.domain.match.reader.SinglesMatchReader;
import org.badminton.domain.domain.match.store.DoublesMatchStore;
import org.badminton.domain.domain.match.store.SinglesMatchStore;
import org.badminton.domain.domain.match.vo.LeagueMatchSetRedisKey;
import org.badminton.domain.domain.match.vo.MatchRedisKey;
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

	public void setMatchSetScore(Long leagueId, Long matchId, int setNumber, Score score) {
		League league = leagueReader.readLeagueById(leagueId);

		LeagueSetsScoreInProgressInfo leagueSetsScoreInProgressInfo = null;
		if (league.getMatchType() == MatchType.SINGLES) {
			SinglesMatch singlesMatch = singlesMatchReader.getSinglesMatch(matchId);
			singlesMatch.startMatchSet(setNumber);
			singlesMatch.getSinglesSet(setNumber).saveSetScore(score.getLeft(), score.getRight());
			leagueSetsScoreInProgressInfo = LeagueSetsScoreInProgressInfo.fromSinglesMatchAndSet(singlesMatch,
				singlesMatch.getSinglesSet(setNumber));
		} else if (league.getMatchType() == MatchType.DOUBLES) {
			DoublesMatch doublesMatch = doublesMatchReader.getDoublesMatch(matchId);
			doublesMatch.startMatchSet(setNumber);
			doublesMatch.getDoublesSet(setNumber).saveSetScore(score.getLeft(), score.getRight());
			leagueSetsScoreInProgressInfo = LeagueSetsScoreInProgressInfo.fromDoublesMatchAndSet(doublesMatch,
				doublesMatch.getDoublesSet(setNumber));
		}
		setRepository.saveInProgressSet(new LeagueMatchSetRedisKey(leagueId, matchId, setNumber),
			leagueSetsScoreInProgressInfo);
		setRepository.setMatchSetScore(league.getMatchType(), matchId, setNumber, score);
	}

	@Transactional
	public void registerMatchSetScoreInDb(MatchRedisKey key, Score score) {
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

	public void deleteCache(MatchRedisKey key) {
		setRepository.deleteScore(key);
	}

	public void evictLeagueMatchSet(Long leagueId, Long matchId, Integer setNumber) {
		setRepository.evictLeagueMatchSet(new LeagueMatchSetRedisKey(leagueId, matchId, setNumber));
		setRepository.deleteScore(
			new MatchRedisKey(getMatchType(leagueId).getDescription() + matchId, String.valueOf(setNumber)));
	}
}
