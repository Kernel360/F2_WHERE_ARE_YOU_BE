package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchGenerationType;
import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.league.enums.LeagueStatus;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueUpdateInfoWithParticipantCountInfo(
	Long leagueId,

	String leagueName,

	String leagueDescription,

	String fullAddress,
	String region,

	Member.MemberTier requiredTier,

	LeagueStatus leagueStatus,

	MatchType matchType,

	LocalDateTime leagueAt,

	LocalDateTime recruitingCloseAt,

	MatchGenerationType matchGenerationType,

	int playerLimitCount,

	int recruitedMemberCount,

	LocalDateTime createdAt,

	LocalDateTime modifiedAt

) {

	public static LeagueUpdateInfoWithParticipantCountInfo of(
		LeagueUpdateInfo leagueUpdateInfo, int recruitedMemberCount) {

		return new LeagueUpdateInfoWithParticipantCountInfo(
			leagueUpdateInfo.leagueId(),
			leagueUpdateInfo.leagueName(),
			leagueUpdateInfo.leagueDescription(),
			leagueUpdateInfo.fullAddress(),
			leagueUpdateInfo.region(),
			leagueUpdateInfo.requiredTier(),
			leagueUpdateInfo.leagueStatus(),
			leagueUpdateInfo.matchType(),
			leagueUpdateInfo.leagueAt(),
			leagueUpdateInfo.recruitingCloseAt(),
			leagueUpdateInfo.matchGenerationType(),
			leagueUpdateInfo.playerLimitCount(),
			recruitedMemberCount,
			leagueUpdateInfo.createdAt(),
			leagueUpdateInfo.modifiedAt());
	}
}
