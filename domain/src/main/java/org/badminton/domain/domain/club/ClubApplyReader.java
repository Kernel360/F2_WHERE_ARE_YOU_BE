package org.badminton.domain.domain.club;

import java.util.List;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.club.info.ClubApplicantInfo;

public interface ClubApplyReader {

	ClubApply getClubApply(Long clubApplyId);

	void validateApply(String clubToken, String memberToken);

	List<ClubApplicantInfo> getClubApplyByClubToken(String clubToken, ClubApply.ApplyStatus applyStatus);

}
