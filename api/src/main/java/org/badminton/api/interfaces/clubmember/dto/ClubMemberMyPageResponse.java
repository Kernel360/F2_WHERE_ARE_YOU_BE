package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberMyPageInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClubMemberMyPageResponse(
	@Schema(description = "Club ID", example = "1")
	Long clubId,

	@Schema(description = "Club member ID", example = "1")
	Long clubMemberId,

	@Schema(description = "Club name", example = "배드민턴 동호회")
	String clubName,

	@Schema(description = "Member role", example = "ROLE_USER")
	ClubMember.ClubMemberRole role

) {
	public static ClubMemberMyPageResponse toClubMemberMyPageResponse(ClubMemberMyPageInfo info) {
		return new ClubMemberMyPageResponse(
			info.clubId(),
			info.clubMemberId(),
			info.clubName(),
			info.role()
		);
	}
}
