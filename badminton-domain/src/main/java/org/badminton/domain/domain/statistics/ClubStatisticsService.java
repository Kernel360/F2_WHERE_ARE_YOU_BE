package org.badminton.domain.domain.statistics;

import org.badminton.domain.domain.club.info.ClubCreateInfo;

public interface ClubStatisticsService {

	void increaseVisitedClubCount(String clubToken);

	void createStatistic(ClubCreateInfo clubCreateInfo);
}
