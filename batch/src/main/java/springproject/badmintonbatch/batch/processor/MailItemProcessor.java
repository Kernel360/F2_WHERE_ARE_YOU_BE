package springproject.badmintonbatch.batch.processor;

import org.badminton.domain.domain.mail.entity.Mail;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MailItemProcessor implements ItemProcessor<Mail, Mail> {
	@Override
	public Mail process(Mail mail) {
		Mail.MailStatus mailStatus = mail.getMailStatus();
		if (mailStatus.equals(Mail.MailStatus.PENDING)) {
			return mail;
		}
		return null;
	}
}

