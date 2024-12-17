package org.badminton.infrastructure.statistics;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.badminton.domain.domain.club.vo.ClubCache;
import org.badminton.domain.domain.club.vo.ClubRedisKey;
import org.badminton.domain.domain.statistics.ClubStatistics;
import org.badminton.domain.domain.statistics.ClubStatisticsReader;
import org.badminton.domain.domain.statistics.ClubStatisticsRepositoryCustom;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubStatisticsReaderImpl implements ClubStatisticsReader {
	private static final String POPULAR_TOP10_REDIS_KEY = ClubRedisKey.getTop10PopularKey();
	private static final String ACTIVITY_TOP10_REDIS_KEY = ClubRedisKey.getTop10ActivityKey();
	private final ClubStatisticsRepository clubStatisticsRepository;
	private final ClubStatisticsRepositoryCustom clubStatisticsRepositoryCustom;
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public ClubStatistics readClubStatistics(String clubToken) {
		return clubStatisticsRepository.findByClubClubToken(clubToken);
	}

	@Override
	public List<Long> findAllClubId() {
		return clubStatisticsRepositoryCustom.findAllClubId();
	}

	@Override
	public ClubStatistics findByClubId(Long clubId) {
		return clubStatisticsRepository.findByClubClubId(clubId);
	}

	@Override
	public List<ClubStatistics> findAll() {
		return clubStatisticsRepository.findAll();
	}

	@Override
	public List<ClubCache> readTop10PopularClub() {

		Object cachedClubs = redisTemplate.opsForValue().get(POPULAR_TOP10_REDIS_KEY);

		if (cachedClubs != null) {
			return objectMapper.convertValue(cachedClubs, new TypeReference<List<ClubCache>>() {
			});
		}

		return refreshTop10PopularClubsCache();

	}

	@Override
	public List<ClubCache> refreshTop10PopularClubsCache() {
		Pageable pageable = PageRequest.of(0, 10);
		List<ClubCache> top10ByOrderByPopularityScoreDesc = clubStatisticsRepository
			.findTopPopularClubs(pageable);

		redisTemplate.opsForValue()
			.set(POPULAR_TOP10_REDIS_KEY, top10ByOrderByPopularityScoreDesc, 1, TimeUnit.MINUTES);

		return top10ByOrderByPopularityScoreDesc;
	}

	@Override
	public List<ClubCache> readTop10RecentlyActiveClub() {

		Object cachedClubs = redisTemplate.opsForValue().get(ACTIVITY_TOP10_REDIS_KEY);

		if (cachedClubs != null) {
			return objectMapper.convertValue(cachedClubs, new TypeReference<List<ClubCache>>() {
			});
		}

		return refreshActivityClubsCache();
	}

	@Override
	public List<ClubCache> refreshActivityClubsCache() {
		Pageable pageable = PageRequest.of(0, 10);
		List<ClubCache> top10RecentlyActiveClubStatistics = clubStatisticsRepository
			.findTop10ByActivityClubs(pageable);

		redisTemplate.opsForValue()
			.set(ACTIVITY_TOP10_REDIS_KEY, top10RecentlyActiveClubStatistics, 1, TimeUnit.MINUTES);

		return top10RecentlyActiveClubStatistics;
	}
}
