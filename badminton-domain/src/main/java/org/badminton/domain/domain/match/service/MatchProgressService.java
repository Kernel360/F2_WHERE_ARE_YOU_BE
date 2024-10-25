package org.badminton.domain.domain.match.service;

import org.badminton.domain.domain.match.command.MatchCommand;
import org.badminton.domain.domain.match.info.SetInfo;

public interface MatchProgressService {

    MatchStrategy makeSinglesOrDoublesMatchStrategy(Long leagueId);

    SetInfo.Main registerSetScoreInMatch(MatchStrategy matchStrategy, Long leagueId, Long matchId, int setIndex,
                                         MatchCommand.UpdateSetScore updateSetScoreCommand);
}
