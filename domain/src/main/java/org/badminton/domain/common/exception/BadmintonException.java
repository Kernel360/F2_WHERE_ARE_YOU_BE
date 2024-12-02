package org.badminton.domain.common.exception;

import org.badminton.domain.common.error.ErrorCode;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class BadmintonException extends RuntimeException {

	private final ErrorCode errorCode;
	private final String errorMessage;

	public BadmintonException(ErrorCode errorCode, String errorDetails) {
		this(errorCode, errorDetails, null);
	}

	public BadmintonException(ErrorCode errorCode, String errorDetails, Exception exception) {
		super(errorCode.getDescription() + errorDetails, exception);
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription() + errorDetails;
	}

	public BadmintonException(ErrorCode errorCode) {
		this(errorCode, (Exception)null);
	}

	public BadmintonException(ErrorCode errorCode, Exception exception) {
		super(errorCode.getDescription(), exception);
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription() + exception.getMessage();
	}

}
