package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class LeagueParticipationAlreadyCanceledException extends BadmintonException {

    public LeagueParticipationAlreadyCanceledException(Long leagueId, Long memberId) {
        super(ErrorCode.LEAGUE_PARTICIPATION_ALREADY_CANCELED,
                "[경기 일정 아이디 : " + leagueId + ", 회원 아이디 : " + memberId + "]");
    }

    public LeagueParticipationAlreadyCanceledException(Long leagueId, Long memberId, Exception e) {
        super(ErrorCode.LEAGUE_PARTICIPATION_ALREADY_CANCELED,
                "[경기 일정 아이디 : " + leagueId + ", 회원 아이디 : " + memberId + "]", e);
    }

}
