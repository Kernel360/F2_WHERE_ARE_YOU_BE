package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class MatchDuplicateException extends BadmintonException {

    public MatchDuplicateException(Long leagueId) {
        super(ErrorCode.MATCH_ALREADY_EXIST, "[경기 일정 아이디 : " + leagueId + "]");
    }

    public MatchDuplicateException(MatchType matchType, Long leagueId) {
        super(ErrorCode.MATCH_ALREADY_EXIST,
                "[경기 타입 : " + matchType.getDescription() + " 경기 일정 아이디 : " + leagueId + "]");
    }

    public MatchDuplicateException(MatchType matchType, Long leagueId, Exception e) {
        super(ErrorCode.MATCH_ALREADY_EXIST,
                "[경기 타입 : " + matchType.getDescription() + " 경기 일정 아이디 : " + leagueId + "]",
                e);

    }
}
