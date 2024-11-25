package org.badminton.infrastructure.match.repository;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.match.SetScoreNotInCacheException;
import org.badminton.domain.domain.match.vo.RedisKey;
import org.badminton.domain.domain.match.vo.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class SetRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private HashOperations<String, String, String> hashOps;

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    public void setMatchSetScore(MatchType matchType, Long matchId, int setNumber, Score score) {
        hashOps.put(matchType.getDescription() + matchId, String.valueOf(setNumber),
                score.toString());
    }

    public Score getMatchSetScore(MatchType matchType, Long matchId, int setNumber) {
        String score = hashOps.get(matchType.getDescription() + matchId, String.valueOf(setNumber));
        if (score == null) {
            throw new SetScoreNotInCacheException(matchType, matchId, setNumber);
        }
        return new Score(score);
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
}
