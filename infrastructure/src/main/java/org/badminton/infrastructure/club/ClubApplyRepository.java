package org.badminton.infrastructure.club;

import org.badminton.domain.domain.club.entity.ClubApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubApplyRepository extends JpaRepository<ClubApply, Long> {

	ClubApply findByClubApplyId(Long clubApplyId);

	boolean existsByClubClubTokenAndMemberMemberTokenAndStatus(String clubToken, String memberToken,
		ClubApply.ApplyStatus status);

	Page<ClubApply> findAllByClubClubTokenAndStatus(String clubToken, ClubApply.ApplyStatus status, Pageable pageable);

	boolean existsByClubClubTokenAndMemberMemberToken(String clubToken, String memberToken);
}
