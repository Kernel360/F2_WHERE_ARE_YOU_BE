package org.badminton.domain.domain.statistics.event;

import org.badminton.domain.domain.statistics.ClubStatisticsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateClubEventHandler {
	private final ClubStatisticsService clubStatisticsService;

	@TransactionalEventListener
	public void createClubEventListener(CreateClubEvent event) {
		clubStatisticsService.createStatistic(event.getClubCreateInfo());
	}
}
