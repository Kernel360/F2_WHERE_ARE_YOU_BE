package springproject.badmintonbatch.batch.reader;

import java.util.List;

import org.badminton.domain.domain.mail.entity.Mail;
import org.badminton.infrastructure.mail.MailRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailItemReader implements ItemReader<Mail> {

	private final MailRepository mailRepository;
	private List<Mail> sendEmail;

	@Override
	public Mail read() {
		if (sendEmail == null || sendEmail.isEmpty()) {
			sendEmail = mailRepository.findByMailStatus(Mail.MailStatus.PENDING);
		}

		return sendEmail.isEmpty() ? null : sendEmail.remove(0);
	}
}
