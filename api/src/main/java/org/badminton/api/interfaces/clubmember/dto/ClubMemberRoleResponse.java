package org.badminton.api.interfaces.clubmember.dto;

import java.util.List;

public record ClubMemberRoleResponse(
	List<ClubMemberResponse> ROLE_OWNER,
	List<ClubMemberResponse> ROLE_MEMBER,
	List<ClubMemberResponse> ROLE_MANAGER
) {
}
