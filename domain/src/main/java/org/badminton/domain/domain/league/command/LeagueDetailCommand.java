package org.badminton.domain.domain.league.command;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.league.info.LeagueDetailInfo;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueDetailCommand(
	Long leagueId,

	String leagueName,

	String description,

	String leagueLocation,

	Member.MemberTier requiredTier,

	LeagueStatus leagueStatus,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingClosedAt,

	int playerLimitCount,

	MatchGenerationType matchGenerationType,  //RANDOM, TIER, AGE

	Club club
) {
	public static LeagueDetailCommand toCommand(LeagueDetailInfo league) {
		return new LeagueDetailCommand(
			league.leagueId(),
			league.leagueName(),
			league.description(),
			league.leagueLocation(),
			league.requiredTier(),
			league.leagueStatus(),
			league.matchType(),
			league.leagueAt(),
			league.recruitingClosedAt(),
			league.playerLimitCount(),
			league.matchGenerationType(),
			league.club()
		);
	}

	public League toEntity() {
		return new League(
			this.leagueId,
			this.leagueName,
			this.description,
			this.leagueLocation,
			this.requiredTier,
			this.leagueStatus,
			this.matchType,
			this.leagueAt,
			this.recruitingClosedAt,
			this.playerLimitCount,
			this.matchGenerationType,
			this.club
		);
	}
}
