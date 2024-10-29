package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueParticipationNotExistException extends BadmintonException {
    public LeagueParticipationNotExistException(Long leagueId, Long memberId) {
        super(ErrorCode.LEAGUE_NOT_PARTICIPATED, "[경기 일정 아이디] : " + leagueId + "[회원 아이디] : " + memberId);
    }

    public LeagueParticipationNotExistException(Long leagueId, Long memberId, Exception e) {
        super(ErrorCode.LEAGUE_NOT_PARTICIPATED, "[경기 일정 아이디] : " + leagueId + "[회원 아이디] : " + memberId, e);
    }

}
