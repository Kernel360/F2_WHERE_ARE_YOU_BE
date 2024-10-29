package org.badminton.api.common.exception;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class EmptyFileException extends BadmintonException {
    public EmptyFileException() {
        super(ErrorCode.FILE_NOT_EXIST);
    }

    public EmptyFileException(Exception exception) {
        super(ErrorCode.FILE_NOT_EXIST, exception);
    }
}
