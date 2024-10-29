package org.badminton.domain.domain.league;

import org.badminton.domain.domain.league.entity.LeagueRecord;

public interface LeagueRecordReader {
	LeagueRecord getLeagueRecord(String memberToken);
}
