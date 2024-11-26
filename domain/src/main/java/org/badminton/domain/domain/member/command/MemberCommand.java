package org.badminton.domain.domain.member.command;

import org.badminton.domain.domain.member.entity.Member;
import org.badminton.domain.domain.member.entity.MemberAuthorization;

public record MemberCommand(
	MemberAuthorization authorization,

	String name,

	String email,

	String providerId,

	String profileImage
) {

	public Member toEntity() {
		return new Member(this.email, this.name, this.providerId, this.profileImage, this.authorization);
	}
}
