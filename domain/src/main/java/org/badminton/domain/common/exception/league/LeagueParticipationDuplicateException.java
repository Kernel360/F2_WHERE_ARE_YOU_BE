package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueParticipationDuplicateException extends BadmintonException {

    public LeagueParticipationDuplicateException(Long leagueId, String memberToken) {
        super(ErrorCode.LEAGUE_ALREADY_PARTICIPATED, "[경기 일정 아이디 : " + leagueId + ", 회원 토큰 : " + memberToken + "]");
    }

    public LeagueParticipationDuplicateException(Long leagueId, String memberToken, Exception e) {
        super(ErrorCode.LEAGUE_ALREADY_PARTICIPATED, "[경기 일정 아이디 : " + leagueId + ", 회원 토큰 : " + memberToken + "]", e);
    }
}
