package org.badminton.domain.domain.club;

import org.badminton.domain.domain.clubmember.info.ApproveApplyInfo;
import org.badminton.domain.domain.clubmember.info.ClubApplyInfo;
import org.badminton.domain.domain.clubmember.info.RejectApplyInfo;

public interface ClubApplyService {
	ApproveApplyInfo approveApplying(Long clubApplyId);

	RejectApplyInfo rejectApplying(Long clubApplyId);

	ClubApplyInfo applyClub(String memberToken, String clubToken, String applyReason);

}
