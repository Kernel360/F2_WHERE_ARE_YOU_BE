package org.badminton.domain.domain.statistics;

import java.util.List;

import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;

public interface ClubStatisticsService {

	void increaseVisitedClubCount(String clubToken);

	void createStatistic(ClubCreateInfo clubCreateInfo);

	List<Long> clubIdByList();

	void updateLeagueCountByClubIdAndCount(Long clubId, int count);

	void updateByCountAndClubId(Long clubId, int count);

	List<ClubCardInfo> getTop10PopularClub();

	List<ClubCardInfo> getTop10RecentlyActiveClub();
}
