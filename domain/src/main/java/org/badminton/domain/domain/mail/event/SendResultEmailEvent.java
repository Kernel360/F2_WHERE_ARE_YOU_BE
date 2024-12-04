package org.badminton.domain.domain.mail.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SendResultEmailEvent {
	private final long clubApplyId;
	private final boolean clubJoinApproval;
}
