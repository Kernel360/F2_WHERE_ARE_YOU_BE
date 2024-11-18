package org.badminton.domain.domain.match.service;

import org.badminton.domain.domain.match.info.BracketInfo;

public interface BracketGenerationService {

	// 1. 경기 상태를 확인한다.
	void checkLeagueRecruitingStatus(Long leagueId);

	// 2. Singles, Doubles 에 따라 Match Strategy 를 만든다.
	MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId);

	BracketInfo makeBracket(MatchStrategy matchStrategy, Long leagueId);

	void initMatch(MatchStrategy matchStrategy, Long matchId);
}