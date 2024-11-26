package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueCreateInfo(
	Long leagueId,
	String leagueName,
	String description,
	String fullAddress,
	String region,
	Member.MemberTier requiredTier,
	LeagueStatus status,
	MatchType matchType,
	LocalDateTime leagueAt,
	LocalDateTime recruitingClosedAt,
	int playerLimitCount,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	MatchGenerationType matchGenerationType
) {

	public static LeagueCreateInfo from(League league) {
		return new LeagueCreateInfo(
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
			league.getCreatedAt(),
			league.getModifiedAt(),
			league.getMatchGenerationType()
		);
	}
}
