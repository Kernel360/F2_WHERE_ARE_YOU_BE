package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;

public record ClubMemberResponse(
	Long clubMemberId,
	String image,
	String name,
	ClubMember.ClubMemberRole role
) {

	public static ClubMemberResponse entityToClubMemberResponse(ClubMemberInfo clubMemberInfo) {
		return new ClubMemberResponse(
			clubMemberInfo.clubMemberId(),
			clubMemberInfo.image(),
			clubMemberInfo.name(),
			clubMemberInfo.role()
		);
	}
}
