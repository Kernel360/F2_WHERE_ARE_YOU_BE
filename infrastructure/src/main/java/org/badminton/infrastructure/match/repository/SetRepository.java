package org.badminton.infrastructure.match.repository;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.badminton.domain.common.enums.MatchType;
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

    public void setMatchSetScore(MatchType matchType, Long matchId, int setNumber, List<Integer> setScore) {
        hashOps.put(matchType.getDescription() + matchId, String.valueOf(setNumber),
                setScore.get(0) + ":" + setScore.get(1));
    }

    public String getMatchSetScore(MatchType matchType, Long matchId, int setNumber) {
        return hashOps.get(matchType.getDescription() + matchId, String.valueOf(setNumber));
    }
}
