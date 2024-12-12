package org.badminton.infrastructure.club;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.badminton.domain.common.exception.club.ClubNotExistException;
import org.badminton.domain.domain.club.ClubReader;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.info.ClubCardInfo;
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
	public Page<Club> readAllClubs(Pageable pageable) {
		return clubRepository.findAllByIsClubDeletedIsFalse(pageable);
	}

	@Override
	public Page<Club> keywordSearch(String keyword, Pageable pageable) {
		return clubRepository.findAllByClubNameContainingIgnoreCaseAndIsClubDeletedIsFalse(keyword, pageable);
	}

	@Override
	public List<ClubCardInfo> readRecentlyCreatedClubs() {

		Object cachedClubs = redisTemplate.opsForValue().get(RECENTLY_TOP10_REDIS_KEY);

		if (cachedClubs != null) {
			return objectMapper.convertValue(cachedClubs, new TypeReference<List<ClubCardInfo>>() {
			});
		}

		return refreshRecentlyCreatedClubsCache();

	}

	@Override
	@Transactional(readOnly = true)
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
