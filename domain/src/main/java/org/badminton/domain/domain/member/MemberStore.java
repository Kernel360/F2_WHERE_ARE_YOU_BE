package org.badminton.domain.domain.member;

import org.badminton.domain.domain.member.entity.Member;

public interface MemberStore {
	void store(Member member);
}
