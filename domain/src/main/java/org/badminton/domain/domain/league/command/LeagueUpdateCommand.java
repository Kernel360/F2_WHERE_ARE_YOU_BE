package org.badminton.domain.domain.league.command;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.info.LeagueDetailInfo;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueUpdateCommand(
	String leagueName,

	String description,

	String leagueLocation,

	Member.MemberTier tierLimit,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingClosedAt,

	int playerLimitCount,

	MatchGenerationType matchGenerationType
) {

	public LeagueUpdatedCommand toUpdate(LeagueDetailInfo origin) {
		return new LeagueUpdatedCommand(
			this.leagueName,
			this.description,
			this.leagueLocation,
			this.tierLimit,
			this.matchType,
			this.leagueAt,
			this.recruitingClosedAt,
			this.playerLimitCount,
			this.matchGenerationType,
			origin.club()
		);
	}
}
