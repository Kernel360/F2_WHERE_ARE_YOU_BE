package org.badminton.api.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggerFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {

		var req = new ContentCachingRequestWrapper((HttpServletRequest)request);
		var res = new ContentCachingResponseWrapper((HttpServletResponse)response);

		chain.doFilter(req, res);

		// request 정보

		var headerNames = req.getHeaderNames();
		var headerValues = new StringBuilder();

		headerNames.asIterator().forEachRemaining(headerKey -> {

			var headerValue = req.getHeader(headerKey);

			// authorization-token : ??? , user-agent : ???
			headerValues.append("[").append(headerKey).append(" : ").append(headerValue).append("]");

		});

		var requestBody = new String(req.getContentAsByteArray());
		var uri = req.getRequestURI();
		var method = req.getMethod();

		log.info(">>>>> uri : {}, method : {}, header : {}, body : {}", uri, method, headerValues, requestBody);

		// response 정보
		var responseHeaderValues = new StringBuilder();

		res.getHeaderNames().forEach(headerKey -> {
			var headerValue = req.getHeader(headerKey);
			responseHeaderValues.append("[").append(headerKey).append(" : ").append(headerValue).append("]");

		});

		var responseBody = new String(res.getContentAsByteArray());

		log.info("<<<<< uri : {}, method : {}, header : {}, body : {}", uri, method, responseHeaderValues,
			responseBody);

		res.copyBodyToResponse();

	}
}
