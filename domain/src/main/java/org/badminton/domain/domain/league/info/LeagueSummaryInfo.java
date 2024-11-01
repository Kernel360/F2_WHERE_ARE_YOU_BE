package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueSummaryInfo(
	Long leagueId,

	String leagueName,

	String leagueDescription,

	String fullAddress,

	String region,

	Member.MemberTier requiredTier,

	LeagueStatus leagueStatus,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingClosedAt,

	int playerLimitCount,

	MatchGenerationType matchGenerationType,

	LocalDateTime createdAt,

	LocalDateTime modifiedAt

) {
	public static LeagueSummaryInfo from(League league) {
		return new LeagueSummaryInfo(
			league.getLeagueId(),
			league.getLeagueName(),
			league.getDescription(),
			league.getAddress().getFullAddress(),
			league.getAddress().getRegion(),
			league.getRequiredTier(),
			league.getLeagueStatus(),
			league.getMatchType(),
			league.getLeagueAt(),
			league.getRecruitingClosedAt(),
			league.getPlayerLimitCount(),
			league.getMatchGenerationType(),
			league.getCreatedAt(),
			league.getModifiedAt()
		);
	}
}
