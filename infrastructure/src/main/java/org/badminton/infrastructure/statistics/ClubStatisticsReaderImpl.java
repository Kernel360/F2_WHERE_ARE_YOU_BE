package org.badminton.infrastructure.statistics;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.vo.ClubRedisKey;
import org.badminton.domain.domain.statistics.ClubStatistics;
import org.badminton.domain.domain.statistics.ClubStatisticsReader;
import org.badminton.domain.domain.statistics.ClubStatisticsRepositoryCustom;
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
	public List<ClubCardInfo> readTop10PopularClub() {

		Object cachedClubs = redisTemplate.opsForValue().get(POPULAR_TOP10_REDIS_KEY);

		if (cachedClubs != null) {
			return objectMapper.convertValue(cachedClubs, new TypeReference<List<ClubCardInfo>>() {
			});
		}

		return refreshTop10PopularClubsCache();

	}

	@Override
	public List<ClubCardInfo> refreshTop10PopularClubsCache() {
		List<ClubStatistics> top10ByOrderByPopularityScoreDesc = clubStatisticsRepository
			.findTop10ByOrderByPopularityScoreDesc();

		List<ClubCardInfo> top10ClubCardInfo = top10ByOrderByPopularityScoreDesc.stream()
			.map(clubStatistics -> ClubCardInfo.from(clubStatistics.getClub()))
			.toList();

		redisTemplate.opsForValue().set(POPULAR_TOP10_REDIS_KEY, top10ClubCardInfo, 1, TimeUnit.MINUTES);

		return top10ClubCardInfo;
	}

	@Override
	public List<ClubCardInfo> readTop10RecentlyActiveClub() {

		Object cachedClubs = redisTemplate.opsForValue().get(ACTIVITY_TOP10_REDIS_KEY);

		if (cachedClubs != null) {
			return objectMapper.convertValue(cachedClubs, new TypeReference<List<ClubCardInfo>>() {
			});
		}

		return refreshActivityClubsCache();
	}

	@Override
	public List<ClubCardInfo> refreshActivityClubsCache() {
		List<ClubStatistics> top10RecentlyActiveClubStatistics = clubStatisticsRepository
			.findTop10ByOrderByActivityScoreDesc();

		List<ClubCardInfo> top10ClubCardInfo = top10RecentlyActiveClubStatistics.stream()
			.map(clubStatistics -> ClubCardInfo.from(clubStatistics.getClub()))
			.toList();

		redisTemplate.opsForValue().set(ACTIVITY_TOP10_REDIS_KEY, top10ClubCardInfo, 1, TimeUnit.MINUTES);

		return top10ClubCardInfo;
	}
}
