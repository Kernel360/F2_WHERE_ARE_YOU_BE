package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueDetailInfo(
	Long leagueId,

	String leagueName,

	String description,

	String fullAddress,
	
	String region,

	Member.MemberTier requiredTier,

	LeagueStatus leagueStatus,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingClosedAt,

	int playerLimitCount,

	MatchGenerationType matchGenerationType,  //RANDOM, TIER, AGE

	Club club
) {
	public LeagueDetailInfo(League league) {
		this(
			league.getLeagueId(),
			league.getLeagueName(),
			league.getDescription(),
			league.getAddress().getFullAddress(),
			league.getAddress().getRegion(),
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

	public static LeagueDetailInfo from(League league) {
		return new LeagueDetailInfo(league);
	}

}
