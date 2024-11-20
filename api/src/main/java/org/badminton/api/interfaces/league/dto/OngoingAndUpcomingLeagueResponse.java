package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.league.info.OngoingAndUpcomingLeagueInfo;
import org.badminton.domain.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

public record OngoingAndUpcomingLeagueResponse(

	@Schema(description = "리그 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
	Long leagueId,

	@Schema(description = "경기 시간", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime leagueAt,

	@Schema(description = "경기 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String leagueName,

	@Schema(description = "경기 설명", requiredMode = Schema.RequiredMode.REQUIRED)
	String description,

	@Schema(description = "매치 타입", requiredMode = Schema.RequiredMode.REQUIRED)
	MatchType matchType,

	@Schema(description = "제한 참가 인원", requiredMode = Schema.RequiredMode.REQUIRED)
	int playerLimitCount,

	@Schema(description = "신청된 인원 수", requiredMode = Schema.RequiredMode.REQUIRED)
	int recruitedMemberCount,

	@Schema(description = "경기 상태", requiredMode = Schema.RequiredMode.REQUIRED)
	LeagueStatus leagueStatus,

	@Schema(description = "경기 티어", requiredMode = Schema.RequiredMode.REQUIRED)
	Member.MemberTier requiredTier,

	@Schema(description = "동호회 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
	String clubToken
) {

	public static OngoingAndUpcomingLeagueResponse from(OngoingAndUpcomingLeagueInfo ongoingAndUpcomingLeagueInfo) {
		return new OngoingAndUpcomingLeagueResponse(
			ongoingAndUpcomingLeagueInfo.getLeagueId(),
			ongoingAndUpcomingLeagueInfo.getLeagueAt(),
			ongoingAndUpcomingLeagueInfo.getLeagueName(),
			ongoingAndUpcomingLeagueInfo.getDescription(),
			ongoingAndUpcomingLeagueInfo.getMatchType(),
			ongoingAndUpcomingLeagueInfo.getPlayerLimitCount(),
			ongoingAndUpcomingLeagueInfo.getRecruitedMemberCount(),
			ongoingAndUpcomingLeagueInfo.getLeagueStatus(),
			ongoingAndUpcomingLeagueInfo.getRequiredTier(),
			ongoingAndUpcomingLeagueInfo.getClubToken()
		);
	}
}
