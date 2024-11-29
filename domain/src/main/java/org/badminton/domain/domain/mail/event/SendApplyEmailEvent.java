package org.badminton.domain.domain.mail.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SendApplyEmailEvent {
	private final String clubToken;
	private final String memberToken;
}
