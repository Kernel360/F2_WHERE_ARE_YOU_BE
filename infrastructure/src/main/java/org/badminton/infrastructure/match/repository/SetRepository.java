package org.badminton.infrastructure.match.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.vo.RedisKey;
import org.badminton.domain.domain.match.vo.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SetRepository {

	private final RedisTemplate<String, Object> redisTemplate2;

	@Autowired
	private StringRedisTemplate redisTemplate;

	private HashOperations<String, String, String> hashOps;

	private static final String IN_PROGRESS_MATCH_PREFIX = "IN_PROGRESS_LEAGUE_ID_";

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	public void setMatchSetScore(MatchType matchType, Long matchId, int setNumber, Score score) {
		hashOps.put(matchType.getDescription() + matchId, String.valueOf(setNumber),
			score.toString());
	}

	public Optional<Score> getMatchSetScore(MatchType matchType, Long matchId, int setNumber) {
		String score = hashOps.get(matchType.getDescription() + matchId, String.valueOf(setNumber));
		if (score == null) {
			return Score.emptyScore();
		}
		return Optional.of(new Score(score));
	}

	public Map<RedisKey, Score> getAllScores() {
		Set<String> keys = redisTemplate.keys("*게임*");

		Map<RedisKey, Score> scores = new HashMap<>();
		if (keys == null) {
			return null;
		}
		for (String key : keys) {
			log.info("*****key: {}", key);
			Map<String, String> setNumberAndScore = hashOps.entries(key);
			setNumberAndScore.forEach((field, value) -> {
				log.info("*****field: {}, value: {}", field, value);
				scores.put(new RedisKey(key, field), new Score(value));
			});
		}
		return scores;
	}

	public void deleteScore(RedisKey redisKey) {
		hashOps.delete(redisKey.getKey(), redisKey.getField());
	}

	public void saveSet(Long leagueId, LeagueSetsScoreInProgressInfo leagueSetsScoreInProgressInfo) {
		redisTemplate2.opsForValue().set(IN_PROGRESS_MATCH_PREFIX + leagueId, leagueSetsScoreInProgressInfo, 1,
			TimeUnit.MINUTES);
	}
}
