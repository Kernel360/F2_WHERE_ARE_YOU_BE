package org.badminton.infrastructure.clubmember;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

	List<ClubMember> findAllByClubClubToken(String clubToken);

	Optional<ClubMember> findByDeletedFalseAndMemberMemberToken(String memberToken);

	Optional<ClubMember> findByClubClubTokenAndMemberMemberToken(String clubToken, String memberToken);

	boolean existsByMember_MemberTokenAndDeletedFalse(String memberToken);

	List<ClubMember> findAllByMemberMemberToken(String memberToken);

	List<ClubMember> findAllByClubClubTokenAndBannedFalseAndDeletedFalse(String clubToken);

	boolean existsByMemberMemberTokenAndClubClubToken(String memberToken, String clubToken);

	Optional<ClubMember> findByClubMemberId(Long clubMemberId);

	List<ClubMember> findAllByDeletedFalseAndClubClubToken(String clubToken);

	@Query("SELECT COUNT(cm) FROM ClubMember cm WHERE cm.member.memberToken = :memberToken AND cm.role = 'ROLE_OWNER'")
	long countByMemberIdAndRoleOwner(@Param("memberToken") String memberToken);
}

