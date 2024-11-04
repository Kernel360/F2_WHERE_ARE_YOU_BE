package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueOwnerCannotCancelLeagueParticipationException extends BadmintonException {
    public LeagueOwnerCannotCancelLeagueParticipationException(String memberToken, Long leagueId) {
        super(ErrorCode.LEAGUE_OWNER_CANNOT_CANCEL_LEAGUE_PARTICIPATION,
                "[회원 토큰 : " + memberToken + " 경기 아이디 : " + leagueId + "]");
    }

    public LeagueOwnerCannotCancelLeagueParticipationException(String memberToken, Long leagueId, Exception e) {
        super(ErrorCode.LEAGUE_OWNER_CANNOT_CANCEL_LEAGUE_PARTICIPATION,
                "[회원 토큰 : " + memberToken + " 경기 아이디 : " + leagueId + "]", e);
    }
}
