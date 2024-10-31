package org.badminton.domain.domain.league.info;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.entity.LeagueParticipant;

public record LeagueParticipantInfo(
	Long leagueId,
	String memberToken,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static LeagueParticipantInfo from(LeagueParticipant leagueParticipant) {
		return new LeagueParticipantInfo(
			leagueParticipant.getLeague().getLeagueId(),
			leagueParticipant.getMember().getMemberToken(),
			leagueParticipant.getCreatedAt(),
			leagueParticipant.getModifiedAt()
		);
	}
}
