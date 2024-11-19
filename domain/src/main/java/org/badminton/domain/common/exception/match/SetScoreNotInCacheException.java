package org.badminton.domain.common.exception.match;

import org.badminton.domain.common.enums.MatchType;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class SetScoreNotInCacheException extends BadmintonException {

    public SetScoreNotInCacheException(MatchType matchType, Long matchId, int setNumber) {
        super(ErrorCode.SET_NOT_EXIST_IN_CACHE,
                "[경기 타입 : " + matchType.getDescription() + " 매치 아이디 : " + matchId + " 세트 번호 : " + setNumber + "]");
    }

    public SetScoreNotInCacheException(MatchType matchType, Long matchId, int setNumber, Exception e) {
        super(ErrorCode.SET_NOT_EXIST_IN_CACHE,
                "[경기 타입 : " + matchType.getDescription() + " 매치 아이디 : " + matchId + " 세트 번호 : " + setNumber + "]", e);
    }
}
