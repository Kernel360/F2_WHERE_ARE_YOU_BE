package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.league.enums.LeagueStatus;

public class LeagueCannotBeUpdated extends BadmintonException {
    public LeagueCannotBeUpdated(Long leagueId, LeagueStatus leagueStatus) {
        super(ErrorCode.LEAGUE_CANNOT_BE_UPDATED,
                "[경기 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + "]");
    }

    public LeagueCannotBeUpdated(Long leagueId, LeagueStatus leagueStatus, Exception e) {
        super(ErrorCode.LEAGUE_CANNOT_BE_UPDATED,
                "[경기 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + "]", e);
    }
}
