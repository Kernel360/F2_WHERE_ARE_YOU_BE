package org.badminton.api.common.exception;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;

public class FileSizeOverException extends BadmintonException {
	public FileSizeOverException(long fileSize) {
		super(ErrorCode.FILE_SIZE_OVER, "[입력한 파일 사이즈 : " + fileSize + ", 최대 파일 사이즈 : 2.5MB]");
	}

	public FileSizeOverException(Exception exception) {
		super(ErrorCode.FILE_SIZE_OVER, exception);
	}
}
