package org.badminton.domain.domain.statistics;

import java.util.List;

public interface ClubStatisticsReader {

	ClubStatistics getClubStaticsWithIncrease(String clubToken);

	List<Long> findAllClubId();

	ClubStatistics findByClubId(Long clubId);

	List<ClubStatistics> findAll();
}
