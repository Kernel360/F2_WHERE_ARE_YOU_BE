package org.badminton.api.application.club;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.badminton.domain.domain.club.ClubService;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.vo.ClubRedisKey;
import org.badminton.domain.domain.statistics.ClubStatistics;
import org.badminton.domain.domain.statistics.ClubStatisticsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubRankFacade {
	private static final String POPULAR_TOP10_REDIS_KEY = ClubRedisKey.getTop10PopularKey();
	private final ClubService clubService;
	private final ClubStatisticsService clubStatisticsService;
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	@Transactional(readOnly = true)
	public List<ClubCardInfo> getTop10PopularClub() {
		Object cachedClubs = redisTemplate.opsForValue().get(POPULAR_TOP10_REDIS_KEY);

		if (cachedClubs != null) {
			return objectMapper.convertValue(cachedClubs, new TypeReference<List<ClubCardInfo>>() {
			});
		}

		List<ClubStatistics> top10PopularClubStatistics = clubStatisticsService.getTop10PopularClubStatistics();

		List<ClubCardInfo> top10ClubCardInfo = top10PopularClubStatistics.stream()
			.map(clubStatistics -> ClubCardInfo.from(clubStatistics.getClub()))
			.toList();

		redisTemplate.opsForValue().set(POPULAR_TOP10_REDIS_KEY, top10ClubCardInfo, 1, TimeUnit.HOURS);

		return top10ClubCardInfo;
	}

	@Transactional(readOnly = true)
	public List<ClubCardInfo> getTop10RecentlyActiveClub() {
		List<ClubStatistics> top10RecentlyActiveClubStatistics = clubStatisticsService.getTop10RecentlyActiveClubStatistics();

		return top10RecentlyActiveClubStatistics.stream()
			.map(clubStatistics ->
				ClubCardInfo.from(clubStatistics.getClub()))
			.toList();
	}

	@Transactional(readOnly = true)
	public List<ClubCardInfo> getTop10RecentlyCreatedClub() {
		return clubService.getRecentlyCreatedClub();
	}
}
