package org.badminton.domain.domain.league.command;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueCancelCommand(
	Long leagueId,

	String leagueName,

	String description,

	String leagueLocation,

	Member.MemberTier tierLimit,

	LeagueStatus leagueStatus,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingClosedAt,

	int playerLimitCount,

	MatchGenerationType matchGenerationType,

	Club club
) {

}
