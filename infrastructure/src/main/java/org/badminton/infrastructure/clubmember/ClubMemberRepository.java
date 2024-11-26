package org.badminton.infrastructure.clubmember;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
	ClubMember findByClubClubIdAndMemberId(Long clubId, Long memberId);

	List<ClubMember> findAllByClubClubToken(String clubToken);

	List<ClubMember> findAllByDeletedFalseAndMemberMemberToken(String memberToken);

	List<ClubMember> findAllByDeletedFalseAndBannedFalseAndMemberMemberToken(String memberToken);

	Optional<ClubMember> findByClubClubTokenAndMemberMemberToken(String clubToken, String memberToken);

	boolean existsByClubClubTokenAndMemberMemberTokenAndDeletedFalse(String clubToken, String memberToken);

	boolean existsByClubClubTokenAndMemberMemberTokenAndDeletedTrueAndBannedTrue(String clubToken, String memberToken);

	List<ClubMember> findAllByClubClubTokenAndDeletedFalse(String clubToken);

	boolean existsByMemberMemberTokenAndClubClubToken(String memberToken, String clubToken);

	Optional<ClubMember> findByClubMemberId(Long clubMemberId);

	Integer countByClubClubIdAndDeletedFalse(Long clubId);

	ClubMember findByClubClubTokenAndRole(String clubToken, ClubMember.ClubMemberRole role);

	Integer countByClubClubTokenAndDeletedFalse(String clubToken);
}

