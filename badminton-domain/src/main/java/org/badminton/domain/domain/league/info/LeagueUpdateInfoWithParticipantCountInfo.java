package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueUpdateInfoWithParticipantCountInfo(
	Long leagueId,
	LocalDateTime leagueAt,
	String leagueName,
	MatchType matchType,
	Member.MemberTier requiredTier,
	LocalDateTime recruitingCloseAt,
	int playerLimitCount,
	int participantCount
) {
	public LeagueUpdateInfoWithParticipantCountInfo(LeagueUpdateInfo leagueUpdateInfo, int participantCount) {
		this(
			leagueUpdateInfo.leagueId(),
			leagueUpdateInfo.leagueAt(),
			leagueUpdateInfo.leagueName(),
			leagueUpdateInfo.matchType(),
			leagueUpdateInfo.requiredTier(),
			leagueUpdateInfo.recruitingCloseAt(),
			leagueUpdateInfo.playerLimitCount(),
			participantCount
		);
	}

	public static LeagueUpdateInfoWithParticipantCountInfo leagueByDateInfoWithParticipantCountInfo(
		LeagueUpdateInfo leagueUpdateInfo, int participantCount) {
		return new LeagueUpdateInfoWithParticipantCountInfo(leagueUpdateInfo, participantCount);
	}
}
