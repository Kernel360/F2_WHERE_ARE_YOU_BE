package org.badminton.api.interfaces.league.dto;

import java.time.LocalDateTime;

import org.badminton.domain.domain.league.info.LeagueParticipantInfo;

public record LeagueParticipantResponse(
	Long leagueId,
	String memberToken,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {

	public static LeagueParticipantResponse fromLeagueParticipantInfo(LeagueParticipantInfo leagueParticipant) {
		return new LeagueParticipantResponse(leagueParticipant.leagueId(),
			leagueParticipant.memberToken(),
			leagueParticipant.createdAt(), leagueParticipant.modifiedAt());
	}
}
