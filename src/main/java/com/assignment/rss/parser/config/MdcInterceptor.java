package com.assignment.rss.parser.config;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * The Class MdcInterceptor.
 *
 * @author sachi
 */
public class MdcInterceptor implements HandlerInterceptor {

	/** The Constant CORRELATION_ID. */
	private static final String CORRELATION_ID = "CorrelationId";

	/**
	 * Pre handle.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param handler  the handler
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String correlationId = getCorrelationId();
		MDC.put(CORRELATION_ID, correlationId);
		return true;
	}

	/**
	 * After completion.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param handler  the handler
	 * @param ex       the ex
	 * @throws Exception the exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		MDC.remove(CORRELATION_ID);
	}

	/**
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	private String getCorrelationId() {
		return UUID.randomUUID().toString();
	}
}
