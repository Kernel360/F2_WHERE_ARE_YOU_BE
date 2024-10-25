package org.badminton.domain.domain.member.info;

import org.badminton.domain.domain.member.entity.MemberAuthorization;
import org.badminton.domain.domain.member.entity.Member;

public record MemberInfo(

	String  memberToken,

	String authorization,

	String name,

	String email,

	String providerId,

	String profileImage
) {
	public static MemberInfo toMemberInfo(Member member) {
		return new MemberInfo(member.getMemberToken(), member.getAuthorization(),member.getName(), member.getEmail(),member.getProviderId(),member.getProfileImage());
	}

}
