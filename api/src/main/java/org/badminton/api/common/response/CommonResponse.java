package org.badminton.api.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.badminton.domain.common.error.ErrorCode;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private Result result;
    private T data;
    private ErrorCode errorCode;
    private String errorMessageForLog;
    private String errorMessageForClient;

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder()
                .result(Result.SUCCESS)
                .data(data)
                .build();
    }

    public static CommonResponse fail(ErrorCode errorCode, String errorMessageForClient, String errorMessageForLog) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .errorMessageForClient(errorMessageForClient)
                .errorMessageForLog(errorMessageForLog)
                .errorCode(errorCode)
                .data("비어 있습니다.")
                .build();
    }

    public static CommonResponse fail(ErrorCode errorCode) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .errorCode(errorCode)
                .build();
    }

    public enum Result {
        SUCCESS, FAIL
    }
}