package org.badminton.domain.domain.statistics;

import java.util.List;

import org.badminton.domain.domain.club.vo.ClubCache;

public interface ClubStatisticsReader {

	ClubStatistics readClubStatistics(String clubToken);

	List<Long> findAllClubId();

	ClubStatistics findByClubId(Long clubId);

	List<ClubStatistics> findAll();

	List<ClubCache> readTop10PopularClub();

	List<ClubCache> readTop10RecentlyActiveClub();

	List<ClubCache> refreshTop10PopularClubsCache();

	List<ClubCache> refreshActivityClubsCache();
}
