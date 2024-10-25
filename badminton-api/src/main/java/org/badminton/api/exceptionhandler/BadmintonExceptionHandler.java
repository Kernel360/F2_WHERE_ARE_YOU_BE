package org.badminton.api.exceptionhandler;

import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.badminton.domain.common.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class BadmintonExceptionHandler {
	public static final String FAIL = "FAIL";
	private static final String UNKNOWN_ERROR = "알 수 없는 에러";

	@ExceptionHandler(value = BadmintonException.class)
	public ResponseEntity<ExceptionResponse> handleBadmintonException(
		BadmintonException badmintonException
	) {
		var errorCode = badmintonException.getErrorCode();
		var errorMessage = badmintonException.getErrorMessage();
		var result = badmintonException.getResult();
		log.error(wrapLogMessage(errorCode, errorMessage));
		return ResponseEntity.status(errorCode.getHttpStatusCode())
			.body(
				ExceptionResponse.builder()
					.httpStatusCode(errorCode.getHttpStatusCode())
					.errorCode(errorCode)
					.errorMessage(errorMessage)
					.result(result)
					.build()
			);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleValidationExceptions(
		MethodArgumentNotValidException ex) {

		var errorCode = ErrorCode.VALIDATION_ERROR;
		var errorMessage = ex.getBindingResult().getFieldErrors().stream()
			.map(error -> error.getField() + ": " + error.getDefaultMessage())
			.reduce((msg1, msg2) -> msg1 + ", " + msg2)
			.orElse("입력값 검증에 실패했습니다.");

		log.error(wrapLogMessage(errorCode, errorMessage));

		return ResponseEntity.status(errorCode.getHttpStatusCode())
			.body(ExceptionResponse.builder()
				.httpStatusCode(errorCode.getHttpStatusCode())
				.errorCode(errorCode)
				.errorMessage(errorMessage)
				.result(FAIL)
				.build());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleGlobalException(Exception e) {
		var unknownError = ErrorCode.INTERNAL_SERVER_ERROR;
		log.error(wrapLogMessage(unknownError, e.getMessage()));
		return ResponseEntity
			.status(unknownError.getHttpStatusCode())
			.body(ExceptionResponse.builder()
				.errorMessage(e.getMessage() + UNKNOWN_ERROR)
				.errorCode(unknownError)
				.httpStatusCode(unknownError.getHttpStatusCode())
				.result(FAIL)
				.build());
	}

	private String wrapLogMessage(ErrorCode errorCode, String errorDetails) {
		return "**** 예외 발생: " + errorCode + " > 예외 상세: " + errorDetails;
	}
}
