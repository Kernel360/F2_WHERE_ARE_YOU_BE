package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

public record LeagueByDateResponse(
	@Schema(description = "경기 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
	Long leagueId,
	@Schema(description = "경기 시작 날짜", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime leagueAt,
	@Schema(description = "경기 이름", requiredMode = Schema.RequiredMode.REQUIRED)
	String leagueName,
	@Schema(description = "경기 방식 (SINGLES | DOUBLES)", requiredMode = Schema.RequiredMode.REQUIRED)
	MatchType matchType,
	@Schema(description = "최소 티어, (GOLD | SILVER | BRONZE)", requiredMode = Schema.RequiredMode.REQUIRED)
	Member.MemberTier requiredTier,
	@Schema(description = "모집 마감 날짜", requiredMode = Schema.RequiredMode.REQUIRED)
	LocalDateTime recruitingCloseAt,
	@Schema(description = "참가 제한 인원", requiredMode = Schema.RequiredMode.REQUIRED)
	int playerLimitCount,
	@Schema(description = "현재까지 참여한 인원", requiredMode = Schema.RequiredMode.REQUIRED)
	int participantCount,
	@Schema(description = "현재 경기 상태( ALL | RECRUITING | RECRUITING_COMPLETED | PLAYING | CANCELED | FINISHED)",
		requiredMode = Schema.RequiredMode.REQUIRED)
	LeagueStatus status
) {

	public static LeagueByDateResponse fromLeagueEntity(League league, int recruitedMemberCount) {
		return new LeagueByDateResponse(
			league.getLeagueId(), league.getLeagueAt(), league.getLeagueName(),
			league.getMatchType(), league.getRequiredTier(), league.getRecruitingClosedAt(),
			league.getPlayerLimitCount(), recruitedMemberCount, league.getLeagueStatus()
		);
	}

}
