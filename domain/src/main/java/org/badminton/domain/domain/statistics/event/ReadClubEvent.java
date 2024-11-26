package org.badminton.domain.domain.statistics.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReadClubEvent {

	private final String clubToken;
}
