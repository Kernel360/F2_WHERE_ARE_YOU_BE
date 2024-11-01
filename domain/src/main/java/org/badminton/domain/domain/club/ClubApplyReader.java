package org.badminton.domain.domain.club;

import java.util.List;

import org.badminton.domain.domain.club.entity.ClubApply;

public interface ClubApplyReader {

	ClubApply getClubApply(Long clubApplyId);

	void validateApply(String clubToken, String memberToken);

	List<ClubApply> getClubApplyByClubToken(String clubToken, ClubApply.ApplyStatus applyStatus);

}
