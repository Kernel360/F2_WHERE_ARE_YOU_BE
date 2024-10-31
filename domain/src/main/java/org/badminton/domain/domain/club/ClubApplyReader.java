package org.badminton.domain.domain.club;

import org.badminton.domain.domain.club.entity.ClubApply;

public interface ClubApplyReader {

	ClubApply getClubApply(Long clubApplyId);

	boolean IsExistClubApply(String clubToken, String memberToken);

}
