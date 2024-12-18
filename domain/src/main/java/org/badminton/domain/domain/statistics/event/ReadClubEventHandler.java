package org.badminton.domain.domain.statistics.event;

import org.badminton.domain.domain.statistics.ClubStatisticsService;
import org.badminton.domain.domain.statistics.DistributedLockProcessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReadClubEventHandler {

	private final ClubStatisticsService clubStatisticsService;
	private final DistributedLockProcessor distributedLockProcessor;

	@Async
	@TransactionalEventListener
	public void readClubEventListener(ReadClubEvent event) {
		clubStatisticsService.increaseVisitedClubCount(event.getClubToken());
	}
}
