package org.badminton.api.application.club.strategy;

import java.util.Comparator;
import java.util.List;

import org.badminton.domain.domain.statistics.ClubStatistics;
import org.springframework.stereotype.Component;

@Component
public class PopularClub implements Rank {

	@Override
	public List<ClubStatistics> arrangeByStrategy(List<ClubStatistics> statisticsList) {
		return statisticsList.stream()
			.sorted(Comparator.comparingDouble(ClubStatistics::calculatePopularityScore).reversed())
			.toList();
	}
}
