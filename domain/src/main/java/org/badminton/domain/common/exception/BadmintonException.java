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
		this(errorCode, errorCode.getDescription());
	}

	public BadmintonException(ErrorCode errorCode, Exception exception) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
		log.error(exception.toString(), exception);
	}

}
