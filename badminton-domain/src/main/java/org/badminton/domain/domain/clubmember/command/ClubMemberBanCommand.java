package org.badminton.domain.domain.clubmember.command;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record ClubMemberBanCommand(
	ClubMember.BannedType type,

	String bannedReason
) {
}
