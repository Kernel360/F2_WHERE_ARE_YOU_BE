package org.badminton.infrastructure.club;

import java.util.List;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubApplyRepository extends JpaRepository<ClubApply, Long> {

	ClubApply findByClubApplyId(Long clubApplyId);

	boolean existsByClubClubTokenAndMemberMemberTokenAndStatus(String clubToken, String memberToken,
		ClubApply.ApplyStatus status);

	List<ClubApply> findAllByClubClubTokenAndStatus(String clubToken, ClubApply.ApplyStatus status);
}
