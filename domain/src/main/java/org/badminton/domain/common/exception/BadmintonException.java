package org.badminton.domain.common.exception;

import org.badminton.domain.common.error.ErrorCode;

import lombok.Getter;

@Getter
public class BadmintonException extends RuntimeException {

	private final ErrorCode errorCode;
	private final String errorMessage;
	private final String result = "FAIL";

	public BadmintonException(ErrorCode errorCode, String errorDetails) {
		this(errorCode, errorDetails, null);
	}

	public BadmintonException(ErrorCode errorCode, String errorDetails, Exception e) {
		super(errorCode.getDescription() + errorDetails, e);
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription() + errorDetails;
	}

	public BadmintonException(ErrorCode errorCode) {
		this(errorCode, (Exception)null);
	}

	public BadmintonException(ErrorCode errorCode, Exception e) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}

}
