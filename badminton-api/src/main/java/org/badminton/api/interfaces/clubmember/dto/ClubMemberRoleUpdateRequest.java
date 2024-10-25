package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.domain.domain.clubmember.command.ClubMemberRoleUpdateCommand;
import org.badminton.domain.domain.clubmember.entity.ClubMember;

public record ClubMemberRoleUpdateRequest(
        ClubMember.ClubMemberRole role
) {
	public ClubMemberRoleUpdateCommand of() {
		return new ClubMemberRoleUpdateCommand(this.role());
	}

}
