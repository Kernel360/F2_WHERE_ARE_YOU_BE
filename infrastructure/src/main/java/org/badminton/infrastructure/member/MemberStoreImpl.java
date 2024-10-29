package org.badminton.infrastructure.member;

import org.badminton.domain.domain.member.MemberStore;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {

	private final MemberRepository memberRepository;

	@Override
	public void store(Member member) {
		log.info(
			"Member store details - ID: {}, Name: {},  Email: {}, MemberToken: {}, ProviderId: {}, ProfileImage: {}, Authorization: {}, IsDeleted: {}, CreatedAt: {}, ModifiedAt: {}",
			member.getId(),
			member.getName(),
			member.getEmail(),
			member.getMemberToken(),
			member.getProviderId(),
			member.getProfileImage(),
			member.getAuthorization(),
			member.isDeleted(),
			member.getCreatedAt(),
			member.getModifiedAt()
		);
		memberRepository.save(member);
	}
}
