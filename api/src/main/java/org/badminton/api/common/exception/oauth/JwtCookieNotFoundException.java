package org.badminton.api.common.exception.oauth;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class JwtCookieNotFoundException extends BadmintonException {
    public JwtCookieNotFoundException() {
        super(ErrorCode.JWT_COOKIE_NOT_FOUND);
    }

    public JwtCookieNotFoundException(Exception e) {
        super(ErrorCode.JWT_COOKIE_NOT_FOUND, e);
    }
}

