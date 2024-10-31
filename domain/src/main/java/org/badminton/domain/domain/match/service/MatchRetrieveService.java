package org.badminton.domain.domain.match.service;

import java.util.List;
import org.badminton.domain.domain.match.info.BracketInfo;
import org.badminton.domain.domain.match.info.LeagueSetsScoreInProgressInfo;
import org.badminton.domain.domain.match.info.MatchInfo;
import org.badminton.domain.domain.match.info.MatchSetInfo;
import org.badminton.domain.domain.match.info.SetInfo;

public interface MatchRetrieveService {

    MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId);

    BracketInfo retrieveBracket(MatchStrategy matchStrategy, Long leagueId);

    List<SetInfo.Main> retrieveAllSetsScoreInBracket(MatchStrategy matchStrategy, Long leagueId);

    MatchInfo.SetScoreDetails retrieveAllSetsScoreInMatch(MatchStrategy matchStrategy, Long matchId);

    boolean isMatchInLeague(MatchStrategy matchStrategy, Long leagueId);

    List<LeagueSetsScoreInProgressInfo> retrieveLeagueMatchInProgress(MatchStrategy matchStrategy, Long leagueId);

    MatchSetInfo retrieveMatchSet(MatchStrategy matchStrategy, Long matchId, int setNumber);
}
