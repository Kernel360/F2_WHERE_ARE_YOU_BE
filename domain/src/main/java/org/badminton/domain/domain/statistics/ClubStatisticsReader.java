package org.badminton.domain.domain.statistics;

import java.util.List;

public interface ClubStatisticsReader {

	ClubStatistics readClubStatistics(String clubToken);

	List<Long> findAllClubId();

	ClubStatistics findByClubId(Long clubId);

	List<ClubStatistics> findAll();

	List<ClubStatistics> readTop10PopularClubStatistics();

	List<ClubStatistics> readTop10RecentlyActiveClubStatistics();
}
