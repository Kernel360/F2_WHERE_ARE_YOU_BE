package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.domain.league.enums.LeagueStatus;

public class LeagueCannotBeCanceledWhenIsNotRecruiting extends BadmintonException {
    public LeagueCannotBeCanceledWhenIsNotRecruiting(Long leagueId, LeagueStatus leagueStatus) {
        super(ErrorCode.LEAGUE_CANNOT_BE_CANCELED_WHEN_IS_NOT_RECRUITING,
                "[경기 일정 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + "]");
    }

    public LeagueCannotBeCanceledWhenIsNotRecruiting(Long leagueId, LeagueStatus leagueStatus, Exception e) {
        super(ErrorCode.LEAGUE_CANNOT_BE_CANCELED_WHEN_IS_NOT_RECRUITING,
                "[경기 일정 아이디 : " + leagueId + " 경기 상태 : " + leagueStatus.getDescription() + "]", e);
    }
}
