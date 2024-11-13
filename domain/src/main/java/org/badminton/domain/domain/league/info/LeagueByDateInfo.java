package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueByDateInfo(
	Long leagueId,
	LocalDateTime leagueAt,
	String leagueName,
	MatchType matchType,
	Member.MemberTier requiredTier,
	LocalDateTime recruitingCloseAt,
	int playerLimitCount,
	LeagueStatus status
) {
	public LeagueByDateInfo(League entity) {
		this(
			entity.getLeagueId(),
			entity.getLeagueAt(),
			entity.getLeagueName(),
			entity.getMatchType(),
			entity.getRequiredTier(),
			entity.getRecruitingClosedAt(),
			entity.getPlayerLimitCount()
			, entity.getLeagueStatus()
		);
	}

	public static LeagueByDateInfo leagueByDateEntityToInfo(League entity) {
		return new LeagueByDateInfo(entity);
	}
}
