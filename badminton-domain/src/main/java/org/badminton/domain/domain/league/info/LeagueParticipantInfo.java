package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.entity.LeagueParticipant;

public record LeagueParticipantInfo(
	Long leagueId,
	Long memberId,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static LeagueParticipantInfo from(LeagueParticipant saved) {
		return new LeagueParticipantInfo(
			saved.getLeague().getLeagueId(),
			saved.getMember().getId(),
			saved.getCreatedAt(),
			saved.getModifiedAt()
		);
	}
}
