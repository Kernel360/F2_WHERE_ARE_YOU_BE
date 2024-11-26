package org.badminton.domain.domain.league.command;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueCreateNoIncludeClubCommand(
	String leagueToken,

	String leagueName,

	String description,

	String fullAddress,

	Member.MemberTier tierLimit,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingClosedAt,

	int playerLimitCount,

	MatchGenerationType matchGenerationType
) {
}
