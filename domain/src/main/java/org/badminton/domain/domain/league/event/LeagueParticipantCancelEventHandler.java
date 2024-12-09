package org.badminton.domain.domain.league.event;

import org.badminton.domain.domain.league.LeagueParticipantCancelService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LeagueParticipantCancelEventHandler {
	private final LeagueParticipantCancelService leagueParticipantCancelService;

	@Async
	@TransactionalEventListener
	public void leagueParticipantCancel(LeagueParticipantCancelEvent leagueParticipantCancelEvent) {
		leagueParticipantCancelService.cancelAllLeagueParticipants(leagueParticipantCancelEvent.getClubMemberId());
	}
}
