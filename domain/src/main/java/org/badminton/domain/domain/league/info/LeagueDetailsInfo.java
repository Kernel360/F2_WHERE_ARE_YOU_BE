package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;
import java.util.List;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueDetailsInfo(
	Long leagueId,

	String leagueOwnerToken,

	String leagueName,

	String leagueDescription,

	String fullAddress,

	String region,

	Member.MemberTier requiredTier,

	LeagueStatus leagueStatus,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingClosedAt,

	MatchGenerationType matchGenerationType,

	int playerLimitCount,

	int recruitedMemberCount,

	LocalDateTime createdAt,

	LocalDateTime modifiedAt,

	boolean isMatchCreated,

	List<LeagueParticipantDetailsInfo> leagueParticipants
) {

	public static LeagueDetailsInfo from(
		LeagueSummaryInfo leagueSummaryInfo,
		boolean isMatchCreated,
		int recruitedMemberCount,
		List<LeagueParticipantDetailsInfo> leagueParticipants
	) {
		return new LeagueDetailsInfo(
			leagueSummaryInfo.leagueId(),
			leagueSummaryInfo.leagueOwnerToken(),
			leagueSummaryInfo.leagueName(),
			leagueSummaryInfo.leagueDescription(),
			leagueSummaryInfo.fullAddress(),
			leagueSummaryInfo.region(),
			leagueSummaryInfo.requiredTier(),
			leagueSummaryInfo.leagueStatus(),
			leagueSummaryInfo.matchType(),
			leagueSummaryInfo.leagueAt(),
			leagueSummaryInfo.recruitingClosedAt(),
			leagueSummaryInfo.matchGenerationType(),
			leagueSummaryInfo.playerLimitCount(),
			recruitedMemberCount,
			leagueSummaryInfo.createdAt(),
			leagueSummaryInfo.modifiedAt(),
			isMatchCreated,
			leagueParticipants
		);
	}
}
