package org.badminton.domain.common.response;

import org.badminton.domain.common.error.ErrorCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionResponse {
	private int httpStatusCode;
	private ErrorCode errorCode;
	private String errorMessage;
	private String result;
}
