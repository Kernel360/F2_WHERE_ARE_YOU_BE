package org.badminton.domain.domain.mail;

import org.badminton.domain.domain.club.ClubApplyReader;
import org.badminton.domain.domain.club.ClubReader;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.mail.entity.Mail;
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

	private final ClubReader clubReader;
	private final ClubMemberReader clubMemberReader;
	private final ClubApplyReader clubApplyReader;
	private final MailStore mailStore;

	@Override
	public void prepareClubApplyEmail(String clubToken, String memberToken) {
		clubApplyReader.validateApply(clubToken, memberToken);
		Club club = clubReader.readClub(clubToken);
		ClubMember clubOwner = clubMemberReader.getClubOwner(clubToken);
		String ownerEmail = clubOwner.getMember().getEmail();
		String clubName = club.getClubName();
		String title = APPLY_CLUB_TITLE + clubName;
		createMessage(ownerEmail, title, APPLY_CLUB_MESSAGE);
	}

	@Override
	public void prepareClubApplyResultEmail(Long clubApplyId, boolean isApproved) {
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
		createMessage(email, title, message);
	}

	private void createMessage(String toEmail, String title, String message) {
		Mail mail = new Mail(title, message, toEmail);
		mailStore.store(mail);
	}
}
