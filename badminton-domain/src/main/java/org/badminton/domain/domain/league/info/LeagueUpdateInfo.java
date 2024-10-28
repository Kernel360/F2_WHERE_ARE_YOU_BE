package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueUpdateInfo(
	Long leagueId,

	String leagueName,

	String leagueDescription,

	String leagueLocation,

	Member.MemberTier requiredTier,

	LeagueStatus leagueStatus,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingCloseAt,

	MatchGenerationType matchGenerationType,

	int playerLimitCount,

	LocalDateTime createdAt,

	LocalDateTime modifiedAt
) {
	public static LeagueUpdateInfo leagueUpdateInfo(League savedLeague) {
		return new LeagueUpdateInfo(
			savedLeague.getLeagueId(),
			savedLeague.getLeagueName(),
			savedLeague.getDescription(),
			savedLeague.getLeagueLocation(),
			savedLeague.getRequiredTier(),
			savedLeague.getLeagueStatus(),
			savedLeague.getMatchType(),
			savedLeague.getLeagueAt(),
			savedLeague.getRecruitingClosedAt(),
			savedLeague.getMatchGenerationType(),
			savedLeague.getPlayerLimitCount(),
			savedLeague.getCreatedAt(),
			savedLeague.getModifiedAt()
		);
	}
}
