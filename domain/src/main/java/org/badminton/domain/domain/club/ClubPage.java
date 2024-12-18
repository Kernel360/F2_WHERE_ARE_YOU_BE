package org.badminton.domain.domain.club;

import java.util.List;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.vo.ClubCache;
import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class ClubPage {
	private final Page<ClubCache> clubCaches;
	private final Page<Club> club;

	private final List<Club> clubList;
	private final List<ClubCache> clubCacheList;

	public Page<ClubCardInfo> clubToRedisPageCardInfo() {
		return this.clubCaches.map(ClubCardInfo::from);

	}

	public Page<ClubCardInfo> clubToPageCardInfo() {
		return this.club.map(ClubCardInfo::from);
	}

	public List<ClubCardInfo> clubListToRedisPageCardInfo() {
		return this.clubCacheList.stream().map(ClubCardInfo::from).toList();

	}

	public List<ClubCardInfo> clubListToPageCardInfo() {
		return this.clubList.stream().map(ClubCardInfo::from).toList();
	}
}
