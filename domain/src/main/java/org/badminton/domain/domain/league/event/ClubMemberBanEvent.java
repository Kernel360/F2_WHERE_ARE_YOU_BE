package org.badminton.domain.domain.league.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClubMemberBanEvent {
	private final long clubMemberId;
}
