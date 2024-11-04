package org.badminton.domain.domain.mail.entity;

import org.badminton.domain.common.AbstractBaseTime;

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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mailId;

	private String title;

	private String message;

	private String toEmail;

	@Enumerated(EnumType.STRING)
	private MailStatus mailStatus;

	public Mail(String title, String message, String toEmail) {
		super();
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
