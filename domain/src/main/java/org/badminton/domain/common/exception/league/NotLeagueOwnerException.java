package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class NotLeagueOwnerException extends BadmintonException {

    public NotLeagueOwnerException(Long leagueId, String memberToken) {
        super(ErrorCode.UNAUTHORIZED_USER_FOR_BRACKET_GENERATION,
                "[경기 아이디 : " + leagueId + " 회원 토큰 : " + memberToken + "]");
    }

    public NotLeagueOwnerException(String memberToken) {
        super(ErrorCode.NOT_LEAGUE_OWNER, "[회원 토큰 : " + memberToken + "]");
    }

    public NotLeagueOwnerException(String memberToken, Exception e) {
        super(ErrorCode.NOT_LEAGUE_OWNER, "[회원 토큰 : " + memberToken + "]", e);
    }
}
