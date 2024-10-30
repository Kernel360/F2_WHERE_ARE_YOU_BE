package org.badminton.infrastructure.member;

import org.badminton.domain.common.exception.member.MemberNotExistException;
import org.badminton.domain.domain.member.MemberReader;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader {

	private final MemberRepository memberRepository;

	@Override
	public Member getMember(String memberToken) {
		return memberRepository.findByMemberToken(memberToken)
			.orElseThrow(() -> new MemberNotExistException(memberToken));
	}
}
