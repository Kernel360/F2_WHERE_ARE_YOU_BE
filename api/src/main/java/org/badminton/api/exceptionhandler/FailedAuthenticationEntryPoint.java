package org.badminton.api.exceptionhandler;

import java.io.IOException;
import java.util.Arrays;

import org.badminton.api.common.response.CommonResponse;
import org.badminton.domain.common.error.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
		String errorMessageForLog = Arrays.toString(authException.getStackTrace());
		var commonResponse =
			CommonResponse.fail(
				errorCode,
				errorCode.getDescription(),
				String.valueOf(authException)
			);
		log.error(wrapLogMessage(errorCode, errorMessageForLog));
		response.setContentType("application/json;charset=UTF-8");
		String jsonResponse = objectMapper.writeValueAsString(commonResponse);
		response.getWriter().write(jsonResponse);
	}

	private String wrapLogMessage(ErrorCode errorCode, String errorDetails) {
		return "**** 예외 발생: " + errorCode + " > 예외 상세: " + errorDetails;
	}
}
