package org.badminton.domain.domain.league.command;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.vo.Address;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueUpdatedCommand(
	String leagueName,

	String description,

	String fullAddress,

	Member.MemberTier tierLimit,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingClosedAt,

	int playerLimitCount,

	MatchGenerationType matchGenerationType,

	Club club
) {
	public League toEntity() {
		Address address = new Address(this.fullAddress);
		return new League(
			this.leagueName,
			this.description,
			address,
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
