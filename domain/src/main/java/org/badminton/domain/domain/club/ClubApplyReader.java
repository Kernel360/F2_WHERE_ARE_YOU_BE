package org.badminton.domain.domain.club;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.club.info.ClubApplicantInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubApplyReader {

	ClubApply getClubApply(Long clubApplyId);

	void validateApply(String clubToken, String memberToken);

	void validateEmailApply(String clubToken, String memberToken);

	Page<ClubApplicantInfo> getClubApplyByClubToken(String clubToken, ClubApply.ApplyStatus applyStatus,
		Pageable pageable);
}
