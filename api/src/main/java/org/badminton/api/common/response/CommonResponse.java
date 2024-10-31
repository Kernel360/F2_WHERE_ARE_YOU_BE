package org.badminton.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.badminton.domain.common.error.ErrorCode;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private Result result;

    // success 일 때 가지는 상태
    private T data;

    // fail 일 때 가지는 상태
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