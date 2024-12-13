package org.badminton.domain.domain.club;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.vo.RedisClub;
import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class ClubPage {
	private final Page<RedisClub> redisClub;
	private final Page<Club> club;

	public Page<ClubCardInfo> clubToRedisPageCardInfo() {
		return this.redisClub.map(ClubCardInfo::from);

	}

	public Page<ClubCardInfo> clubToPageCardInfo() {
		return this.club.map(ClubCardInfo::from);
	}
}
