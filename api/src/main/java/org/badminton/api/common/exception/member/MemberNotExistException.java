package org.badminton.api.common.exception.member;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class MemberNotExistException extends BadmintonException {

	public MemberNotExistException(String memberToken) {
		super(ErrorCode.MEMBER_NOT_EXIST, "[회원 토큰 : " + memberToken + "]");
	}

	public MemberNotExistException(String memberToken, Exception e) {
		super(ErrorCode.MEMBER_NOT_EXIST, "[회원 토큰 : " + memberToken + "]", e);
	}
}
