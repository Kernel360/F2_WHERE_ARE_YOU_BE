package org.badminton.api.application.mail;

import org.badminton.domain.domain.club.ClubApplyReader;
import org.badminton.domain.domain.club.ClubReader;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailService {

	public static final String APPLY_CLUB_TITLE = "동호회에 가입 신청이 생겼습니다 ";
	public static final String APPLY_CLUB_MESSAGE = "동호회 가입신청을 확인해주세요";
	public static final String APPROVE_APPLY_CLUB_TITLE = "동호회 가입 신청이 승인되었습니다 ";
	public static final String APPROVE_APPLY_CLUB_MESSAGE = "동호회 가입 신청이 승인되었습니다 ";
	public static final String REJECT_APPLY_CLUB_TITLE = "동호회 가입 신청이 거부되었습니다 ";
	public static final String REJECT_APPLY_CLUB_MESSAGE = "동호회 가입 신청이 거부되었습니다 ";

	@Value("${custom.spring.mail.username}")
	private static String fromAddress;
	private final ClubReader clubReader;
	private final ClubMemberReader clubMemberReader;
	private final ClubApplyReader clubApplyReader;
	private JavaMailSender mailSender;

	public void sendClubApplyEmail(String clubToken) {

		Club club = clubReader.readClub(clubToken);
		ClubMember clubOwner = clubMemberReader.getClubOwner(clubToken);
		String ownerEmail = clubOwner.getMember().getEmail();
		String clubName = club.getClubName();

		String title = APPLY_CLUB_TITLE + clubName;
		String message = APPLY_CLUB_MESSAGE;
		mailSender.send(createMessage(ownerEmail, title, message));
	}

	public void sendClubApplyResultEmail(Long clubApplyId, boolean isApproved) {
		String title;
		String message;
		ClubApply clubApply = clubApplyReader.getClubApply(clubApplyId);
		String clubName = clubApply.getClub().getClubName();
		String email = clubApply.getMember().getEmail();

		if (isApproved) {
			title = APPROVE_APPLY_CLUB_TITLE;
			message = APPROVE_APPLY_CLUB_MESSAGE + clubName;
		} else {
			title = REJECT_APPLY_CLUB_TITLE;
			message = REJECT_APPLY_CLUB_MESSAGE + clubName;
		}
		mailSender.send(createMessage(email, title, message));
	}

	public SimpleMailMessage createMessage(String to, String title, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom(fromAddress);
		message.setSubject(title);
		message.setText(text);
		return message;
	}
}
