package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.entity.LeagueParticipant;

public record LeagueParticipantResponse(
	Long leagueId,
	Long clubMemberId,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {

	public static LeagueParticipantResponse entityToLeagueParticipantResponse(LeagueParticipant entity) {
		return new LeagueParticipantResponse(entity.getLeague().getLeagueId(),
			entity.getClubMember().getClubMemberId(),
			entity.getCreatedAt(), entity.getModifiedAt());
	}
}
