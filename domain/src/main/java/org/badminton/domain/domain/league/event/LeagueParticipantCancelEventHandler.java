package org.badminton.domain.domain.league.event;

import org.badminton.domain.domain.league.LeagueParticipantService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LeagueParticipantCancelEventHandler {
	private final LeagueParticipantService leagueParticipantService;

	@Async
	@TransactionalEventListener
	public void leagueParticipantCancel(LeagueParticipantCancelEvent leagueParticipantCancelEvent) {
		leagueParticipantService.cancelAllLeagueParticipants(leagueParticipantCancelEvent.getClubMemberId());
	}
}
