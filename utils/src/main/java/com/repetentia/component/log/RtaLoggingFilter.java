package com.repetentia.component.log;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RtaLoggingFilter extends CommonsRequestLoggingFilter {
	private String contextPath;
	private final AntPathMatcher pathMatcher;
	private List<String> urlPatterns;

	public RtaLoggingFilter() {
		setIncludeHeaders(true);
		setIncludeQueryString(true);
		setIncludePayload(true);
		setIncludeClientInfo(true);
		setMaxPayloadLength(1024);
		pathMatcher = new AntPathMatcher();
		
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
		contextPath = servletContext.getContextPath();
		urlPatterns = List.of(contextPath + "/static/**");
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		for (String urlPattern:urlPatterns) {
			String path = request.getRequestURI();
			boolean isMatch = pathMatcher.match(urlPattern, path);
			if (isMatch) {
				log.trace("# excluding url - {}", path);
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean shouldNotFilterErrorDispatch() {
		return super.shouldNotFilterErrorDispatch();
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
