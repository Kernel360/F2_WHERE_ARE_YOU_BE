package org.badminton.api.interfaces.league.dto;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.league.info.LeagueParticipantDetailsInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record LeagueParticipantDetailsResponse(
	@Schema(description = "경기 참여자 회원 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
	String memberToken,
	@Schema(description = "경기 참여자 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String name,
	@Schema(description = "경기 참여자 프로필 이미지", requiredMode = Schema.RequiredMode.REQUIRED)
	String profileImage,
	@Schema(description = "경기 참여자 동호회 권한", requiredMode = Schema.RequiredMode.REQUIRED)
	ClubMember.ClubMemberRole role
) {
	public static LeagueParticipantDetailsResponse from(LeagueParticipantDetailsInfo leagueParticipant) {
		return new LeagueParticipantDetailsResponse(
			leagueParticipant.memberToken(),
			leagueParticipant.name(),
			leagueParticipant.profileImage(),
			leagueParticipant.role()
		);
	}
}
