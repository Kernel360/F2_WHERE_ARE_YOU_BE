package org.badminton.infrastructure.mail;

import org.badminton.domain.domain.mail.MailStore;
import org.badminton.domain.domain.mail.entity.Mail;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailStoreImpl implements MailStore {
	private final MailRepository mailRepository;

	@Override
	public void store(Mail mail) {
		mailRepository.save(mail);
	}
}
