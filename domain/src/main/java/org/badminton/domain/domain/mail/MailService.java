package org.badminton.domain.domain.mail;

public interface MailService {
	void prepareClubApplyEmail(String clubToken);

	void prepareClubApplyResultEmail(Long clubApplyId, boolean isApproved);
}
