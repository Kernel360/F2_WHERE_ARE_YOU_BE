package org.badminton.api.common.response;

import org.badminton.domain.common.error.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
	private Result result;
	private T data;
	private String message;
	private String errorCode;

	public static <T> CommonResponse<T> success(T data) {
		return CommonResponse.<T>builder()
			.result(Result.SUCCESS)
			.data(data)
			.build();
	}

	public static <T> CommonResponse<T> success(T data, String message) {
		return CommonResponse.<T>builder()
			.result(Result.SUCCESS)
			.data(data)
			.message(message)
			.build();
	}

	public static CommonResponse fail(String message, String errorCode) {
		return CommonResponse.builder()
			.result(Result.FAIL)
			.message(message)
			.errorCode(errorCode)
			.build();
	}

	public static CommonResponse fail(ErrorCode errorCode) {
		return CommonResponse.builder()
			.result(Result.FAIL)
			.message(errorCode.getDescription())
			.errorCode(errorCode.name())
			.build();
	}

	public enum Result {
		SUCCESS, FAIL
	}
}