package org.badminton.domain.domain.clubmember.command;

public record ClubMemberStatusCommand(
	String clubToken,
	Long clubMemberId
) {
}
