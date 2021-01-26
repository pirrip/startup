package com.repetentia.web.startup.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.repetentia.support.log.Marker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
//@Order(1)
public class RtaLoggingFilter extends CommonsRequestLoggingFilter {
	public RtaLoggingFilter() {
		log.debug(Marker.CONFIG, "# RTA LOGGING FILTER");
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
