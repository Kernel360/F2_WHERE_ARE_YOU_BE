package org.badminton.domain.domain.club;

import org.badminton.domain.domain.clubmember.info.ApplyClubInfo;
import org.badminton.domain.domain.clubmember.info.ApproveApplyInfo;
import org.badminton.domain.domain.clubmember.info.RejectApplyInfo;

public interface ClubApplyService {
	ApproveApplyInfo approveApplying(Long clubApplyId);

	RejectApplyInfo rejectApplying(Long clubApplyId);

	ApplyClubInfo applyClub(String memberToken, String clubToken, String applyReason);

}
