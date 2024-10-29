package org.badminton.domain.domain.member.command;

import org.badminton.domain.domain.member.entity.Member;

public record MemberUpdateCommand(
	String profileImageUrl
) {
}
