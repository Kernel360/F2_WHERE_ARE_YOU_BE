package org.badminton.domain.domain.clubmember.command;

import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record ClubMemberRoleUpdateCommand(
	ClubMember.ClubMemberRole role

) {
}
