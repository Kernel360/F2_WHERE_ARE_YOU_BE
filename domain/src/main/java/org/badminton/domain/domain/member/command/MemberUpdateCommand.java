package org.badminton.domain.domain.member.command;

public record MemberUpdateCommand(
	String profileImageUrl,
	String name
) {
}
