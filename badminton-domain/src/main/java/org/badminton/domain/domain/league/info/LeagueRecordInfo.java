package org.badminton.domain.domain.league.info;

import org.badminton.domain.domain.league.entity.LeagueRecord;

public record LeagueRecordInfo(int winCount,
							   int loseCount,
							   int drawCount,
							   int matchCount) {
	public static LeagueRecordInfo from(LeagueRecord leagueRecord) {
		return new LeagueRecordInfo(
			leagueRecord.getWinCount(),
			leagueRecord.getLoseCount(),
			leagueRecord.getDrawCount(),
			leagueRecord.getMatchCount()
		);
	}
}
