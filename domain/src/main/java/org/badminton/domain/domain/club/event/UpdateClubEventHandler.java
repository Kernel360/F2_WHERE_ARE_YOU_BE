package org.badminton.domain.domain.club.event;

import org.badminton.domain.domain.club.ClubService;
import org.badminton.domain.domain.statistics.ClubStatisticsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateClubEventHandler {
	private final ClubStatisticsService clubStatisticsService;
	private final ClubService clubService;

	@Async
	@TransactionalEventListener
	public void updateClubEventListener(UpdateClubEvent event) {
		clubStatisticsService.refreshPopularClubsCache();
		clubStatisticsService.refreshRecentlyActivityClubsCache();
		clubService.refreshRecentlyCreatedClubsCache();
	}
}
