package org.badminton.domain.domain.club;

import org.badminton.domain.domain.club.entity.ClubApply;

public interface ClubApplyReader {

	ClubApply getClubApply(Long clubApplyId);

	void validateApply(String clubToken, String memberToken);

}
