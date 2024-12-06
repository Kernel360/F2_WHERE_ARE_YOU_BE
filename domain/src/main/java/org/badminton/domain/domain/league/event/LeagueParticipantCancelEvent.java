package org.badminton.domain.domain.league.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LeagueParticipantCancelEvent {
	private final long clubMemberId;
}
