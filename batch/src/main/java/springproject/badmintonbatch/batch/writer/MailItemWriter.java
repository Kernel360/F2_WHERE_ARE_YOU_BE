package springproject.badmintonbatch.batch.writer;

import org.badminton.domain.domain.mail.entity.Mail;
import org.badminton.infrastructure.mail.MailRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MailItemWriter implements ItemWriter<Mail> {

	@Value("${custom.spring.mail.username}")
	private static String fromAddress;

	private final MailRepository mailRepository;

	private JavaMailSender mailSender;

	@Override
	public void write(Chunk<? extends Mail> mails) {
		for (Mail mail : mails) {
			if (mail.getMailStatus().equals(Mail.MailStatus.PENDING)) {

				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(mail.getToEmail());
				mailMessage.setFrom(fromAddress);
				mailMessage.setSubject(mail.getTitle());
				mailMessage.setText(mail.getMessage());

				mailSender.send(mailMessage);
				mail.sendComplete();
				mailRepository.save(mail);
			}
		}
	}
}
