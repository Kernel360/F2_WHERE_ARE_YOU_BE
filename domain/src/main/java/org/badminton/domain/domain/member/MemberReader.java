package org.badminton.domain.domain.member;

import org.badminton.domain.domain.member.entity.Member;

public interface MemberReader {
	Member getMember(String memberToken);
}
