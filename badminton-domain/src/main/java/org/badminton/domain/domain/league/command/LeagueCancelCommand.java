package org.badminton.domain.domain.league.command;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.league.entity.League;
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
	public static LeagueCancelCommand toCommand(League league) {
		return new LeagueCancelCommand(
			league.getLeagueId(),
			league.getLeagueName(),
			league.getDescription(),
			league.getLeagueLocation(),
			league.getRequiredTier(),
			league.getLeagueStatus(),
			league.getMatchType(),
			league.getLeagueAt(),
			league.getRecruitingClosedAt(),
			league.getPlayerLimitCount(),
			league.getMatchGenerationType(),
			league.getClub()
		);
	}

	public League toEntity() {
		return new League(
			this.leagueId,
			this.leagueName,
			this.description,
			this.leagueLocation,
			this.tierLimit,
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
