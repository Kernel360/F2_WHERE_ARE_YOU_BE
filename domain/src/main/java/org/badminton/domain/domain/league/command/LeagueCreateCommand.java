package org.badminton.domain.domain.league.command;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueCreateCommand(
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
	public static LeagueCreateCommand build(LeagueCreateNoIncludeClubCommand originCommand, Club club) {
		return new LeagueCreateCommand(
			originCommand.leagueName(),
			originCommand.description(),
			originCommand.leagueLocation(),
			originCommand.tierLimit(),
			originCommand.leagueStatus(),
			originCommand.matchType(),
			originCommand.leagueAt(),
			originCommand.recruitingClosedAt(),
			originCommand.playerLimitCount(),
			originCommand.matchGenerationType(),
			club
		);
	}

	public League toEntity() {
		return new League(
			this.leagueName,
			this.description,
			this.leagueLocation,
			this.leagueAt,
			this.tierLimit,
			this.recruitingClosedAt,
			this.playerLimitCount,
			this.matchType,
			this.matchGenerationType,
			this.club
		);
	}

}
