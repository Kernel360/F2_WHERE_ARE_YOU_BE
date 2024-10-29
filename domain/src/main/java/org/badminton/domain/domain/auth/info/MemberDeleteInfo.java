package org.badminton.domain.domain.auth.info;

import org.badminton.domain.domain.member.entity.Member;

public record MemberDeleteInfo(
	String memberToken,
	boolean isDeleted
) {
	public static MemberDeleteInfo fromMemberDeleteInfo(Member member) {
		return new MemberDeleteInfo(member.getMemberToken(), member.isDeleted());
	}
}
