package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueUpdateInfo(
	Long leagueId,

	String leagueName,

	String leagueDescription,

	String leagueLocation,

	Member.MemberTier requiredTier,

	LeagueStatus leagueStatus,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingClosedAt,

	MatchGenerationType matchGenerationType,

	int playerLimitCount,

	int recruitedMemberCount,

	LocalDateTime createdAt,

	LocalDateTime modifiedAt
) {
}
