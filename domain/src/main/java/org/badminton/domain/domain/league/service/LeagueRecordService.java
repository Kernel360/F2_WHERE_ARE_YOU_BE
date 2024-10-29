package org.badminton.domain.domain.league.service;

import org.badminton.domain.domain.league.entity.LeagueRecord;
import org.badminton.domain.domain.member.entity.Member;

public interface LeagueRecordService {
	void initScore(Member member);

	void addWin(String memberToken);

	void addLoss(String memberToken);

	void addDraw(String memberToken);

	LeagueRecord getLeagueRecord(String memberToken);

}
