package org.badminton.domain.domain.member.info;

import org.badminton.domain.domain.member.entity.Member;

public record MemberLogoutInfo(
	String authorization,

	String name,

	String email,

	String providerId,

	String profileImage
) {
	public static MemberLogoutInfo toMemberLogoutInfo(Member member) {
		return new MemberLogoutInfo(member.getAuthorization(), member.getName(), member.getEmail(),
			member.getProviderId(), member.getProfileImage());
	}
}
