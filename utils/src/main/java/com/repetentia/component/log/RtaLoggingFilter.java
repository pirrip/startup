package com.repetentia.component.log;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RtaLoggingFilter extends CommonsRequestLoggingFilter {
	public RtaLoggingFilter() {
		setIncludeHeaders(true);
		setIncludeQueryString(true);
		setIncludePayload(true);
		setIncludeClientInfo(true);
		setMaxPayloadLength(1024);
	}
	@Override
	protected boolean shouldLog(HttpServletRequest request) {
//		return logger.isDebugEnabled();
		return true;
	}

	/**
	 * Writes a log message before the request is processed.
	 */
	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		log.debug("# before Request - {}", message);
	}

	/**
	 * Writes a log message after the request is processed.
	 */
	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		log.debug("# after Request - {}", message);
	}

}
