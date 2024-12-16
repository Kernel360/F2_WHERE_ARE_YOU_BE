package org.badminton.domain.domain.statistics.event;

import org.badminton.domain.domain.statistics.ClubStatisticsService;
import org.badminton.domain.domain.statistics.DistributedLockProcessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReadClubEventHandler {

	private final ClubStatisticsService clubStatisticsService;
	private final DistributedLockProcessor distributedLockProcessor;

	@Async
	@TransactionalEventListener
	public void readClubEventListener(ReadClubEvent event) {
		String lockName = "CLUB_VISIT_LOCK_" + event.getClubToken();
		distributedLockProcessor.execute(lockName, 10000, 10000,
			() -> clubStatisticsService.increaseVisitedClubCount(event.getClubToken())
		);
	}
}
