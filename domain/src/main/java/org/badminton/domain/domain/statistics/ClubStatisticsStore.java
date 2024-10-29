package org.badminton.domain.domain.statistics;

import org.badminton.domain.domain.club.info.ClubCreateInfo;

public interface ClubStatisticsStore {
	void increaseVisitCount(ClubStatistics origin);

	void store(ClubCreateInfo clubCreateInfo);

	void update(ClubStatistics originStatistic);
}
