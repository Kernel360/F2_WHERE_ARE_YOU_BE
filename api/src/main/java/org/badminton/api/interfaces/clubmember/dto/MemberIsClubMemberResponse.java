package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.MemberIsClubMemberInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberIsClubMemberResponse(
	@Schema(description = "동호회 가입 여부", requiredMode = Schema.RequiredMode.REQUIRED)
	boolean isClubMember,
	@Schema(description = "동호회 역할")
	ClubMember.ClubMemberRole role,
	boolean isBanned
) {
	public static MemberIsClubMemberResponse fromMemberIsClubMemberInfo(MemberIsClubMemberInfo memberIsClubMemberInfo) {
		return new MemberIsClubMemberResponse(
			memberIsClubMemberInfo.isClubMember(),
			memberIsClubMemberInfo.role(),
			memberIsClubMemberInfo.isBanned()
		);
	}
}
