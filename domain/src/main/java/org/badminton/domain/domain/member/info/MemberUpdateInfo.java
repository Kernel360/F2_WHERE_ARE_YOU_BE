package org.badminton.domain.domain.member.info;

import org.badminton.domain.domain.member.entity.Member;

public record MemberUpdateInfo(
	String memberToken,

	String authorization,

	String name,

	String email,

	String providerId,

	String profileImage
) {
	public static MemberUpdateInfo fromMemberEntity(Member member) {
		return new MemberUpdateInfo(member.getMemberToken(), member.getAuthorization(), member.getName(),
			member.getEmail(), member.getProviderId(), member.getProfileImage());
	}

}
