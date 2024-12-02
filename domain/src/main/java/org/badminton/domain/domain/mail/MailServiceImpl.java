package org.badminton.domain.domain.mail;

import java.nio.charset.StandardCharsets;

import org.badminton.domain.common.exception.clubmember.ClubMemberAlreadyExistsException;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

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
	private final JavaMailSender mailSender;

	@Override
	public void prepareClubApplyEmail(String clubToken, String memberToken) {

		validateClubMember(clubToken, memberToken);

		Club club = clubReader.readClub(clubToken);
		ClubMember clubOwner = clubMemberReader.getClubOwner(clubToken);

		String senderMailAddress = clubOwner.getMember().getEmail();
		String clubName = club.getClubName();
		String title = APPLY_CLUB_TITLE + clubName;

		sendEmail(senderMailAddress, title, APPLY_CLUB_MESSAGE);

	}

	private void validateClubMember(String clubToken, String memberToken) {
		if (clubMemberReader.checkIsClubMember(memberToken, clubToken)) {
			throw new ClubMemberAlreadyExistsException(clubToken, memberToken);
		}
	}

	@Override
	public void prepareClubApplyResultEmail(Long clubApplyId, boolean isApproved) {

		String title;
		String message;
		ClubApply clubApply = clubApplyReader.getClubApply(clubApplyId);
		String clubName = clubApply.getClub().getClubName();
		String senderMailAddress = clubApply.getMember().getEmail();

		if (isApproved) {
			title = APPROVE_APPLY_CLUB_TITLE;
			message = APPROVE_APPLY_CLUB_MESSAGE + clubName;
		} else {
			title = REJECT_APPLY_CLUB_TITLE;
			message = REJECT_APPLY_CLUB_MESSAGE + clubName;
		}

		sendEmail(senderMailAddress, title, message);
	}

	private void sendEmail(String senderMailAddress, String title, String message) {
		String utf8Message = new String(message.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
		String utf8Title = new String(title.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(senderMailAddress);
		mailMessage.setFrom(fromAddress);
		mailMessage.setSubject(utf8Title);
		mailMessage.setText(utf8Message);

		mailSender.send(mailMessage);
	}
}
