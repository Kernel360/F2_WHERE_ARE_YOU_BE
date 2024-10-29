package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueDetailsInfo(
	Long leagueId,

	String leagueName,

	String leagueDescription,

	String leagueLocation,

	Member.MemberTier requiredTier,

	LeagueStatus leagueStatus,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingClosedAt,

	MatchGenerationType matchGenerationType,

	int playerLimitCount,

	int recruitedMemberCount,

	boolean isParticipatedInLeague,

	LocalDateTime createdAt,

	LocalDateTime modifiedAt,

	boolean isMatchCreated
) {

	public static LeagueDetailsInfo from(
		LeagueSummaryInfo leagueSummaryInfo,
		boolean isMatchCreated,
		boolean isParticipatedInLeague,
		int recruitedMemberCount
	) {
		return new LeagueDetailsInfo(
			leagueSummaryInfo.leagueId(),
			leagueSummaryInfo.leagueName(),
			leagueSummaryInfo.leagueDescription(),
			leagueSummaryInfo.leagueLocation(),
			leagueSummaryInfo.requiredTier(),
			leagueSummaryInfo.leagueStatus(),
			leagueSummaryInfo.matchType(),
			leagueSummaryInfo.leagueAt(),
			leagueSummaryInfo.recruitingClosedAt(),
			leagueSummaryInfo.matchGenerationType(),
			leagueSummaryInfo.playerLimitCount(),
			recruitedMemberCount,
			isParticipatedInLeague,
			leagueSummaryInfo.createdAt(),
			leagueSummaryInfo.modifiedAt(),
			isMatchCreated
		);
	}
}
