package org.badminton.domain.domain.member.info;

import org.badminton.domain.domain.member.entity.Member;

public record SimpleMemberInfo(

	String authorization,

	String name,

	String email,

	Member.MemberTier memberTier,

	String profileImage
) {
	public static SimpleMemberInfo from(Member member) {
		return new SimpleMemberInfo(member.getAuthorization(), member.getName(),
			member.getEmail(), member.getTier(), member.getProfileImage());
	}

}
