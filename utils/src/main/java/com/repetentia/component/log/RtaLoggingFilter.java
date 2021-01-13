package com.repetentia.component.log;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

public class RtaLoggingFilter extends AbstractRequestLoggingFilter {

	@Override
	protected boolean shouldLog(HttpServletRequest request) {
		return logger.isDebugEnabled();
	}

	/**
	 * Writes a log message before the request is processed.
	 */
	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		logger.debug(message);
	}

	/**
	 * Writes a log message after the request is processed.
	 */
	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		logger.debug(message);
	}

}
