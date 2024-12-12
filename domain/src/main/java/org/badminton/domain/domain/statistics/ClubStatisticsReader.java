package org.badminton.domain.domain.statistics;

import java.util.List;

import org.badminton.domain.domain.club.info.ClubCardInfo;

public interface ClubStatisticsReader {

	ClubStatistics readClubStatistics(String clubToken);

	List<Long> findAllClubId();

	ClubStatistics findByClubId(Long clubId);

	List<ClubStatistics> findAll();

	List<ClubCardInfo> readTop10PopularClub();

	List<ClubCardInfo> readTop10RecentlyActiveClub();

	List<ClubCardInfo> refreshTop10PopularClubsCache();

	List<ClubCardInfo> refreshActivityClubsCache();
}
