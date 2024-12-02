package org.badminton.domain.domain.match.service;

import org.badminton.domain.domain.match.info.BracketInfo;

public interface BracketGenerationService {

	void checkLeagueRecruitingStatus(Long leagueId);

	MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId);

	BracketInfo makeBracket(MatchStrategy matchStrategy, Long leagueId, String memberToken);

	void startMatch(MatchStrategy matchStrategy, Long leagueId, Long matchId);
}
