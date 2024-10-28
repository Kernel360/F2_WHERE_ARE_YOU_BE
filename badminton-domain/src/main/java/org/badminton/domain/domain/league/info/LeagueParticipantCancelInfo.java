package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.entity.LeagueParticipant;

public record LeagueParticipantCancelInfo(
	Long leagueId,
	Long clubMemberId,
	LocalDateTime createdAt,
	LocalDateTime deletedAt
) {
	public static LeagueParticipantCancelInfo from(LeagueParticipant result) {
		return new LeagueParticipantCancelInfo(
			result.getLeague().getLeagueId(),
			result.getClubMember().getClubMemberId(),
			result.getCreatedAt(),
			result.getModifiedAt()
		);
	}
}
