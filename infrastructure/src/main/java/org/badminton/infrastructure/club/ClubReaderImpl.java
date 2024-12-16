package org.badminton.infrastructure.club;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.badminton.domain.common.exception.club.ClubNotExistException;
import org.badminton.domain.domain.club.ClubReader;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.vo.ClubCache;
import org.badminton.domain.domain.club.vo.ClubRedisKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubReaderImpl implements ClubReader {
	private static final String RECENTLY_TOP10_REDIS_KEY = ClubRedisKey.getTop10RecentlyKey();
	private final ClubRepository clubRepository;
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public Page<ClubCache> readAllClubs(Pageable pageable) {
		String key = ClubRedisKey.getClubsPageKey(pageable.getPageNumber());
		Object cachedClubs = redisTemplate.opsForValue().get(key);

		if (cachedClubs != null) {
			RedisPage<ClubCache> redisPage = objectMapper.convertValue(cachedClubs,
				new TypeReference<>() {
				});
			return redisPage.toPage(pageable);
		}

		return refreshClubs(key, pageable);
	}

	private Page<ClubCache> refreshClubs(String key, Pageable pageable) {
		Page<Club> clubs = clubRepository.findAllByIsClubDeletedIsFalse(pageable);
		Page<ClubCache> redisClubs = clubs.map(ClubCache::from);
		redisTemplate.opsForValue().set(key, redisClubs, 5, TimeUnit.MINUTES);
		return redisClubs;
	}

	@Override
	public Page<ClubCache> keywordSearch(String keyword, Pageable pageable) {
		Page<Club> clubs = clubRepository.findAllByClubNameContainingIgnoreCaseAndIsClubDeletedIsFalse(keyword,
			pageable);
		return clubs.map(ClubCache::from);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClubCardInfo> readRecentlyCreatedClubs() {

		Object cachedClubs = redisTemplate.opsForValue().get(RECENTLY_TOP10_REDIS_KEY);

		if (cachedClubs != null) {
			return objectMapper.convertValue(cachedClubs, new TypeReference<List<ClubCardInfo>>() {
			});
		}

		return refreshRecentlyCreatedClubsCache();

	}

	@Override
	public List<ClubCardInfo> refreshRecentlyCreatedClubsCache() {
		List<Club> clubs = clubRepository.findTop10ByIsClubDeletedIsFalseOrderByCreatedAtDesc();

		List<ClubCardInfo> clubCardInfos = clubs.stream()
			.map(ClubCardInfo::from)
			.toList();

		redisTemplate.opsForValue().set(RECENTLY_TOP10_REDIS_KEY, clubCardInfos, 1, TimeUnit.MINUTES);

		return clubCardInfos;
	}

	@Override
	public List<Club> readAllClubs() {
		return clubRepository.findAll();
	}

	@Override
	public Club readClub(String clubToken) {
		return clubRepository.findByClubToken(clubToken).orElseThrow(() -> new ClubNotExistException(clubToken));
	}

	@Override
	public Club readClubByClubId(Long clubId) {
		return clubRepository.findByClubId(clubId);
	}

	@Override
	public Club readExistingClub(String clubToken) {
		return clubRepository.findByClubTokenAndIsClubDeletedFalse(clubToken)
			.orElseThrow(() -> new ClubNotExistException(clubToken));
	}

	@Override
	public boolean UniqueClubName(String clubName) {
		return clubRepository.existsByClubNameAndIsClubDeletedFalse(clubName);
	}

}
