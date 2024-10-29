package org.badminton.domain.domain.statistics;

public interface ClubStatisticsReader {
	int countVisited(String clubToken);

	ClubStatistics getClubStaticsWithIncrease(String clubToken);
}
