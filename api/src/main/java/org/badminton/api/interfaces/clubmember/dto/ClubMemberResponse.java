package org.badminton.api.interfaces.clubmember.dto;

import org.badminton.api.interfaces.league.dto.LeagueRecordResponse;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.clubmember.info.ClubMemberInfo;
import org.badminton.domain.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record ClubMemberResponse(
	@Schema(description = "동호회 회원 ID", requiredMode = RequiredMode.REQUIRED)
	Long clubMemberId,
	@Schema(description = "동호회 회원 이미지", requiredMode = RequiredMode.REQUIRED)
	String image,
	@Schema(description = "동호회 회원 이름", requiredMode = RequiredMode.REQUIRED)
	String name,
	@Schema(description = "동호회 회원 role", requiredMode = RequiredMode.REQUIRED)
	ClubMember.ClubMemberRole role,
	@Schema(description = "동호회 회원 경기 전적", requiredMode = RequiredMode.REQUIRED)
	LeagueRecordResponse leagueRecord,
	@Schema(description = "동호회 회원 티어", requiredMode = RequiredMode.REQUIRED)
	Member.MemberTier tier
) {

	public static ClubMemberResponse entityToClubMemberResponse(ClubMemberInfo clubMemberInfo) {
		return new ClubMemberResponse(
			clubMemberInfo.clubMemberId(),
			clubMemberInfo.image(),
			clubMemberInfo.name(),
			clubMemberInfo.role(),
			LeagueRecordResponse.toLeagueRecordResponse(clubMemberInfo.leagueRecord()),
			clubMemberInfo.tier()
		);
	}
}
