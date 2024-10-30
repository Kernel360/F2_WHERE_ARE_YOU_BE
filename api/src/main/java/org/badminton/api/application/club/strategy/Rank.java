package org.badminton.api.application.club.strategy;

import java.util.List;

import org.badminton.domain.domain.statistics.ClubStatistics;

public interface Rank {
	List<ClubStatistics> arrangeByStrategy(List<ClubStatistics> statisticsList);
}
