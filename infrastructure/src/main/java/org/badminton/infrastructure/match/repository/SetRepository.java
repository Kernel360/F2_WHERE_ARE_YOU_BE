package org.badminton.infrastructure.match.repository;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.exception.match.SetScoreNotInCacheException;
import org.badminton.domain.domain.match.vo.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
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

    public Map<String, Score> getAllScores() {
        Map<String, Score> allScores = new HashMap<>();
        for (String key : Objects.requireNonNull(redisTemplate.keys("*"))) {
            Map<String, String> scoresMap = hashOps.entries(key);
            for (Map.Entry<String, String> entry : scoresMap.entrySet()) {
                String mapKey = key + ":" + entry.getKey(); // Redis key + set number
                allScores.put(mapKey, new Score(entry.getValue()));
            }
        }
        return allScores;
    }
}
