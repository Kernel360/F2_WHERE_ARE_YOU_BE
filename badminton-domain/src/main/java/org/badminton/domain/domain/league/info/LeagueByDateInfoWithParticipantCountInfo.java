package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.domain.member.entity.Member;

public record LeagueByDateInfoWithParticipantCountInfo(
	Long leagueId,
	LocalDateTime leagueAt,
	String leagueName,
	MatchType matchType,
	Member.MemberTier requiredTier,
	LocalDateTime recruitingCloseAt,
	int playerLimitCount,
	int participantCount
) {
	public LeagueByDateInfoWithParticipantCountInfo(LeagueByDateInfo leagueByDateInfo, int participantCount) {
		this(
			leagueByDateInfo.leagueId(),
			leagueByDateInfo.leagueAt(),
			leagueByDateInfo.leagueName(),
			leagueByDateInfo.matchType(),
			leagueByDateInfo.requiredTier(),
			leagueByDateInfo.recruitingCloseAt(),
			leagueByDateInfo.playerLimitCount(),
			participantCount
		);
	}

	public static LeagueByDateInfoWithParticipantCountInfo leagueByDateInfoWithParticipantCountInfo(
		LeagueByDateInfo leagueByDateInfo, int participantCount) {
		return new LeagueByDateInfoWithParticipantCountInfo(leagueByDateInfo, participantCount);
	}
}
