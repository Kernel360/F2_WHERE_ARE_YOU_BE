package org.badminton.api.interfaces.league.dto;

import org.badminton.domain.domain.league.info.LeagueRecordInfo;

public record LeagueRecordResponse(
	int winCount,
	int loseCount,
	int drawCount,
	int matchCount
) {
	public static LeagueRecordResponse toLeagueRecordResponse(LeagueRecordInfo leagueRecordInfo) {
		return new LeagueRecordResponse(
			leagueRecordInfo.winCount(),
			leagueRecordInfo.loseCount(),
			leagueRecordInfo.drawCount(),
			leagueRecordInfo.matchCount()
		);
	}

}
