package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueByDateInfo(
	Long leagueId,
	LocalDateTime leagueAt,
	String leagueName,
	MatchType matchType,
	Member.MemberTier requiredTier,
	LocalDateTime closedAt,
	int playerLimitCount,
	int recruitedMemberCount
) {
}
