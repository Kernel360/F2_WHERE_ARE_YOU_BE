package org.badminton.domain.domain.match.service;

import org.badminton.domain.domain.match.info.BracketInfo;

public interface BracketGenerationService {

    // 1. 경기 상태를 확인한다.
    void checkLeagueRecruitingStatus(Long leagueId);

    // 2. Singles, Doubles 에 따라 Match Strategy 를 만든다.
    MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId);

    // 3. 초기 대진표를 만든다. (1차전)
    BracketInfo makeInitialBracket(MatchStrategy matchStrategy, Long leagueId);
}