package org.badminton.domain.domain.club;

public interface ClubApplyService {
	void approveApplying(Long clubApplyId);

	void rejectApplying(Long clubApplyId);

	void applyClub(String memberToken, String clubToken);

}
