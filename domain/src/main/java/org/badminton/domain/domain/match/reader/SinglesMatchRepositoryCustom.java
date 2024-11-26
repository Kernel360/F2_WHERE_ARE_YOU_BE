package org.badminton.domain.domain.match.reader;

import java.util.List;

import org.badminton.domain.domain.match.entity.SinglesMatch;

public interface SinglesMatchRepositoryCustom {
	List<SinglesMatch> findAllCompletedByClubMemberId(Long clubMemberId);
}
