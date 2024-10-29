package org.badminton.infrastructure.member;

import java.util.List;
import java.util.Optional;

import org.badminton.domain.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByProviderId(String providerId);

	Optional<Member> findByMemberToken(String memberToken);

	List<Member> findAllByIsDeletedTrue();
}
