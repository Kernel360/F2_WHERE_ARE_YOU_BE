package org.badminton.domain.domain.mail;

public interface MailService {
	void prepareClubApplyEmail(String clubToken, String memberToken);

	void prepareClubApplyResultEmail(Long clubApplyId, boolean isApproved);
}
