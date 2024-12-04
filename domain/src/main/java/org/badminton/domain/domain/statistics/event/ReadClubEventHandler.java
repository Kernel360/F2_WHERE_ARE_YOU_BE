package org.badminton.domain.domain.statistics.event;

import org.badminton.domain.domain.statistics.ClubStatisticsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReadClubEventHandler {

	private final ClubStatisticsService clubStatisticsService;

	@Async
	@TransactionalEventListener
	public void readClubEventListener(ReadClubEvent event) {
		clubStatisticsService.increaseVisitedClubCount(event.getClubToken());
	}
}
