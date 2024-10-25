package org.badminton.domain.common.exception.member;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class MemberNotJoinedClubException extends BadmintonException {

    public MemberNotJoinedClubException(Long memberId) {
        super(ErrorCode.MEMBER_NOT_JOINED_CLUB, "[회원 아이디 : " + memberId + "]");
    }

    public MemberNotJoinedClubException(Long memberId, Exception e) {
        super(ErrorCode.MEMBER_NOT_JOINED_CLUB, "[회원 아이디 : " + memberId + "]");
    }

}
