package org.badminton.domain.domain.mail;

import org.badminton.domain.domain.mail.entity.Mail;

public interface MailStore {
	void store(Mail mail);
}
