package org.badminton.infrastructure.clubmember;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

	@Query("""
		   SELECT cm
		   FROM ClubMember cm
		   WHERE cm.club.clubToken = :clubToken
		     AND cm.banned = false
		   ORDER BY 
		     CASE cm.role 
		       WHEN org.badminton.domain.domain.clubmember.entity.ClubMember.ClubMemberRole.ROLE_OWNER THEN 1
		       WHEN org.badminton.domain.domain.clubmember.entity.ClubMember.ClubMemberRole.ROLE_MANAGER THEN 2
		       WHEN org.badminton.domain.domain.clubmember.entity.ClubMember.ClubMemberRole.ROLE_USER THEN 3
		       ELSE 4
		     END
		""")
	Page<ClubMember> findAllActiveClubMembersOrderByRole(
		@Param("clubToken") String clubToken, Pageable pageable);

	Page<ClubMember> findAllByClubClubTokenAndBannedTrue(String clubToken, Pageable pageable);

	List<ClubMember> findAllByClubClubToken(String clubToken);

	List<ClubMember> findAllByDeletedFalseAndMemberMemberToken(String memberToken);

	List<ClubMember> findAllByDeletedFalseAndBannedFalseAndMemberMemberToken(String memberToken);

	Optional<ClubMember> findByClubClubTokenAndMemberMemberToken(String clubToken, String memberToken);

	boolean existsByClubClubTokenAndMemberMemberTokenAndDeletedFalse(String clubToken, String memberToken);

	boolean existsByClubClubTokenAndMemberMemberTokenAndBannedTrue(String clubToken, String memberToken);

	List<ClubMember> findAllByClubClubTokenAndDeletedFalse(String clubToken);

	boolean existsByMemberMemberTokenAndClubClubToken(String memberToken, String clubToken);

	Optional<ClubMember> findByClubMemberId(Long clubMemberId);

	Integer countByClubClubIdAndDeletedFalse(Long clubId);

	ClubMember findByClubClubTokenAndRole(String clubToken, ClubMember.ClubMemberRole role);

	Integer countByClubClubTokenAndDeletedFalse(String clubToken);

}

