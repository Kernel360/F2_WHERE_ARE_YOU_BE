package org.badminton.api.common.exception;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class FileSizeOverException extends BadmintonException {
	public FileSizeOverException() {
		super(ErrorCode.FILE_SIZE_OVER);
	}

	public FileSizeOverException(Exception exception) {
		super(ErrorCode.FILE_SIZE_OVER, exception);
	}
}
