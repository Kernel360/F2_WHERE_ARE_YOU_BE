package org.badminton.domain.domain.mail.entity;

import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.common.TokenGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "mail")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mail extends AbstractBaseTime {
	public static final String MAIL_PREFIX = "mail_";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mailId;

	@Column(name = "mailToken")
	private String mailToken;

	private String title;

	private String message;

	private String toEmail;

	@Enumerated(EnumType.STRING)
	private MailStatus mailStatus;

	public Mail(String title, String message, String toEmail) {
		super();
		this.mailToken = TokenGenerator.randomCharacterWithPrefix(MAIL_PREFIX);
		this.title = title;
		this.message = message;
		this.toEmail = toEmail;
		this.mailStatus = MailStatus.PENDING;
	}

	public void sendComplete() {
		this.mailStatus = MailStatus.COMPLETED;
	}

	@Getter
	public enum MailStatus {
		PENDING("준비 중"),
		COMPLETED("완료"),
		FAIL("실패");

		private final String description;

		MailStatus(String description) {
			this.description = description;
		}
	}
}
