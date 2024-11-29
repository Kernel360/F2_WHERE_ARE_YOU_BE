package org.badminton.domain.domain.mail.event;

import org.badminton.domain.domain.mail.MailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SendResultEventHandler {

	private final MailService mailService;

	@Async
	@TransactionalEventListener
	public void sendResultEventListener(SendResultEmailEvent event) {
		mailService.prepareClubApplyResultEmail(event.getClubApplyId(), event.isClubJoinApproval());
	}

}
