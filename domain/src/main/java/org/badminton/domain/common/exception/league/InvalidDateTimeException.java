package org.badminton.domain.common.exception.league;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class InvalidDateTimeException extends BadmintonException {
    public InvalidDateTimeException(String date) {
        super(ErrorCode.INVALID_RESOURCE, "[유효하지 않은 날짜 : " + date + "]");
    }

    public InvalidDateTimeException(String date, Exception exception) {
        super(ErrorCode.INVALID_RESOURCE, "[유효하지 않은 날짜 : " + date + "]", exception);
    }
}
