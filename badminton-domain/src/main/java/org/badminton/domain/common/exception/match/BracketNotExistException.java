package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class BracketNotExistException extends BadmintonException {

    public BracketNotExistException(Long leagueId) {
        super(ErrorCode.BRACKET_NOT_EXIST, "[경기 일정 아이디 : " + leagueId + "]");
    }

    public BracketNotExistException(Long leagueId, Exception e) {
        super(ErrorCode.BRACKET_NOT_EXIST, "[경기 일정 아이디 : " + leagueId + "]", e);
    }
}
