package org.badminton.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.domain.common.error.ErrorCode;
import org.badminton.domain.common.exception.BadmintonException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BadmintonExceptionHandler {
    private static final String UNKNOWN_ERROR = "알 수 없는 에러";


    @ExceptionHandler(value = BadmintonException.class)
    public CommonResponse handleBadmintonException(
            BadmintonException badmintonException
    ) {
        ErrorCode errorCode = badmintonException.getErrorCode();
        String errorMessageForLog = badmintonException.getErrorMessage();
        log.error(wrapLogMessage(errorCode, errorMessageForLog));
        return CommonResponse.fail(errorCode, errorCode.getDescription(), errorMessageForLog);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        String errorMessageForLog = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                .orElse("입력값 검증에 실패했습니다.");

        log.error(wrapLogMessage(errorCode, errorMessageForLog));

        return CommonResponse.fail(errorCode, errorMessageForLog, errorCode.getDescription());
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse handleGlobalException(Exception e) {
        ErrorCode unknownError = ErrorCode.INTERNAL_SERVER_ERROR;
        log.error(wrapLogMessage(unknownError, e.getMessage()));
        return CommonResponse.fail(unknownError, unknownError.getDescription(), e.getMessage());
    }

    private String wrapLogMessage(ErrorCode errorCode, String errorDetails) {
        return "**** 예외 발생: " + errorCode + " > 예외 상세: " + errorDetails;
    }
}
