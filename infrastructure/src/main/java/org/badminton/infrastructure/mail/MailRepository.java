package org.badminton.infrastructure.mail;

import java.util.List;

import org.badminton.domain.domain.mail.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, Long> {
	List<Mail> findByMailStatus(Mail.MailStatus mailStatus);
}
