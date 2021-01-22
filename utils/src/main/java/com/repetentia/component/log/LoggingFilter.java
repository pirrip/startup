//package com.repetentia.component.log;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.text.SimpleDateFormat;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.UUID;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import kr.arcode.web.constants.Constants;
//import kr.arcode.web.util.DateUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Marker;
//import org.slf4j.MarkerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.web.filter.AbstractRequestLoggingFilter;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//import org.springframework.web.util.WebUtils;
//
//
//public class LoggingFilter extends AbstractRequestLoggingFilter {
//
//	public static final String DEFAULT_BEFORE_REQUEST_MESSAGE_PREFIX	= "\n============== ARCODE REQUEST BEGIN         ==============\n";
//	public static final String DEFAULT_BEFORE_REQUEST_MESSAGE_SUFFIX	= "\n============== ARCODE REQUEST END           ==============";
//	public static final String DEFAULT_AFTER_REQUEST_MESSAGE_PREFIX		= "\n============== ARCODE REQUEST-PAYLOAD BEGIN ==============\n";
//	public static final String DEFAULT_AFTER_REQUEST_MESSAGE_SUFFIX		= "\n============== ARCODE REQUEST-PAYLOAD END   ==============";	
//	public static final String DEFAULT_RESPONSE_MESSAGE_PREFIX			= "\n============== ARCODE RESPONSE BEGIN        ==============\n";
//	public static final String DEFAULT_RESPONSE_MESSAGE_SUFFIX			= "\n============== ARCODE RESPONSE END          ==============";
//	
//	public static final String KEY_BEGIN_TIME 	= "__start_time";
//	public static final String KEY_END_TIME 	= "__end_time";
//	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//
//	private static Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
//	private Marker marker = MarkerFactory.getMarker("INOUT");
//	private String beforeMessagePrefix = "\n============== ARCODE REQUEST BEGIN         ==============\n";
//	private String beforeMessageSuffix = "\n============== ARCODE REQUEST END           ==============";
//	private String afterMessagePrefix = "\n============== ARCODE REQUEST-PAYLOAD BEGIN ==============\n";
//	private String afterMessageSuffix = "\n============== ARCODE REQUEST-PAYLOAD END   ==============";
//	private String responseMessagePrefix = "\n============== ARCODE RESPONSE BEGIN        ==============\n";
//	private String responseMessageSuffix = "\n============== ARCODE RESPONSE END          ==============";
//	private String headersFormat = "string";
//	private String exceptUrl;
//	private String[] exceptUrls;
//	private String maskingParam;
//	private Map<String, String> maskingParamMap;
//	private ObjectMapper mapper = new ObjectMapper();
//
//	public LoggingFilter() {
//		setIncludeHeaders(true);
//		setIncludeQueryString(true);
//		setIncludePayload(true);
//		setMaxPayloadLength(1024);
//	}
//
//	public void setExceptUrl(String exceptUrl) {
//		this.exceptUrl = exceptUrl;
//		this.exceptUrls = StringUtils.split(this.exceptUrl, ",");
//	}
//
//	public void setMaskingParam(String maskingParam) {
//		this.maskingParam = maskingParam;
//		Map<String, String> map = new HashMap();
//		String[] var3 = StringUtils.split(this.maskingParam, ",");
//		int var4 = var3.length;
//
//		for(int var5 = 0; var5 < var4; ++var5) {
//			String p = var3[var5];
//			map.put(p, p);
//		}
//
//		this.maskingParamMap = map;
//	}
//
//	protected boolean shouldLog(HttpServletRequest request) {
//		return true;
//	}
//
//	@Override
//	protected void beforeRequest(HttpServletRequest request, String message) {
//		String messageId =(String)request.getAttribute(Constants.KEY_MESSAGE_ID);
//		long startTime = (Long)request.getAttribute(KEY_BEGIN_TIME);
//
//		StringBuilder msg = new StringBuilder();
//		msg.append("REQUEST >>> MESSAGE-ID=[{}], START-TIME=[{}]");
//		msg.append(message);
//
//		logger.info(this.marker, msg.toString(), messageId, DateUtil.asLocalDateTime(startTime).format(DATE_FORMAT));
//
//	}
//
//	@Override
//	protected void afterRequest(HttpServletRequest request, String message) {
//		String messageId =(String)request.getAttribute(Constants.KEY_MESSAGE_ID);
//		long startTime = (Long)request.getAttribute(KEY_BEGIN_TIME);
//
//		StringBuilder msg = new StringBuilder();
//		msg.append("REQUEST >>> MESSAGE-ID=[{}], START-TIME=[{}]");
//		msg.append(message);
//
//		logger.info(this.marker, msg.toString(), messageId, DateUtil.asLocalDateTime(startTime).format(DATE_FORMAT));
//	}
//
//	protected void afterResponse(HttpServletRequest request, HttpServletResponse response, String message) {
//		String messageId = (String)request.getAttribute(Constants.KEY_MESSAGE_ID);
//		long startTime = (Long)request.getAttribute(KEY_BEGIN_TIME);
//		long endTime = (Long)request.getAttribute(KEY_END_TIME);
//
//		StringBuilder msg = new StringBuilder();
//		msg.append("RESPONSE >>> MESSAGE-ID=[{}], START-TIME=[{}], END-TIME=[{}], RUNNING-TIME=[{}ms]");
//		msg.append(message);
//
////		logger.info(this.marker, msg.toString(), new Object[]{messageId, DateUtil.asLocalDateTime(startTime).format(DATE_FORMAT), DateUtil.asLocalDateTime(endTime).format(DATE_FORMAT), endTime - startTime});
//
//
//	}
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//
//		//리소스는 로깅 대상에서 제외한다
//		if(StringUtils.containsAny(request.getServletPath(), this.exceptUrls)) {
//			filterChain.doFilter(request, response);
//			return;
//		}		
//
//		boolean isFirstRequest = !isAsyncDispatch(request);
//		HttpServletRequest requestToUse = request;
//		HttpServletResponse responseToUse = response;
//
//		//요청시 Payload 체크
//		HttpMethod method = HttpMethod.resolve(request.getMethod()); 
//		if(method == HttpMethod.POST || method == HttpMethod.PUT) {
//			if(isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
//				requestToUse = new ContentCachingRequestWrapper(request, getMaxPayloadLength());
//			}
//		}
//
//		//응답시 Payload 체크
//		if(StringUtils.indexOf(request.getHeader(HttpHeaders.ACCEPT), MediaType.APPLICATION_JSON_VALUE) != -1) {
//			if(isFirstRequest && !(response instanceof ContentCachingResponseWrapper)) {
//				responseToUse = new ContentCachingResponseWrapper((HttpServletResponse)response);
//			}
//		}
//
//		boolean shouldLog = shouldLog(requestToUse);
//		if (shouldLog && isFirstRequest) {
//			beforeRequest(requestToUse, getBeforeMessage(requestToUse));
//		}
//		try {
//			filterChain.doFilter(requestToUse, responseToUse);
//		}
//		finally {
//			if (shouldLog && !isAsyncStarted(requestToUse)) {
//
//				this.afterRequest(requestToUse, this.getAfterMessage( requestToUse));
//				this.afterResponse(requestToUse, responseToUse, getResponseMessage( requestToUse, responseToUse));
//
//			}
//		}
//	}
//
//	private String getBeforeMessage(HttpServletRequest request) {
//		String messageId = UUID.randomUUID().toString();
//		long startTime = System.currentTimeMillis();
//		request.setAttribute(Constants.KEY_MESSAGE_ID, messageId);
//		request.setAttribute(KEY_BEGIN_TIME, startTime);
//		return this.createMessage(request, beforeMessagePrefix, beforeMessageSuffix);
//	}
//
//	private String getAfterMessage(HttpServletRequest request) {
//		long endTime = System.currentTimeMillis();
//		request.setAttribute(KEY_END_TIME, endTime);
//		return this.createMessage(request, afterMessagePrefix, afterMessageSuffix);
//	}
//
//	private String getResponseMessage(HttpServletRequest request, HttpServletResponse response) {
//		return createResponseMessage(request, response, responseMessagePrefix, responseMessageSuffix);
//	}
//
//	@Override
//	protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
//		String messageId = (String)request.getAttribute(Constants.KEY_MESSAGE_ID);
//		long startTime = (Long)request.getAttribute(KEY_BEGIN_TIME);
//
//		StringBuilder msg = new StringBuilder();
//		msg.append(prefix);
//		msg.append("message-id=").append(messageId);
//		msg.append(";start-time=").append(DateUtil.asLocalDateTime(startTime).format(DATE_FORMAT));
//		msg.append("uri=").append(request.getRequestURI());
//
//		if (isIncludeQueryString()) {
//			String queryString = request.getQueryString();
//			if (queryString != null) {
//				msg.append('?').append(queryString);
//			}
//		}
//
//		String user;
//		if (isIncludeClientInfo()) {
//			String client = request.getRemoteAddr();
//			if (StringUtils.length(client) > 0) {
//				msg.append(";client=").append(client);
//			}
//			HttpSession session = request.getSession(false);
//			if (session != null) {
//				msg.append(";session=").append(session.getId());
//			}
//			user = request.getRemoteUser();
//			if (user != null) {
//				msg.append(";user=").append(user);
//			}
//		}
//
//		if (isIncludeHeaders()) {
//			Map<String, String> headers = new HashMap();
//			Enumeration headerNames = request.getHeaderNames();
//
//			while(headerNames.hasMoreElements()) {
//				user = (String)headerNames.nextElement();
//				headers.put(user, request.getHeader(user));
//			}
//
//			if (StringUtils.equals(this.headersFormat, "json")) {
//				try {
//					msg.append(";\nheaders=").append(this.mapper.writeValueAsString(headers));
//				} catch (JsonProcessingException e) {
//					e.printStackTrace();
//				}
//			} else {
//				msg.append(";\nheaders=").append(headers);
//			}
//		}
//
//		if (isIncludePayload()) {
//			if (request instanceof ContentCachingRequestWrapper) {
//				ContentCachingRequestWrapper wrapper =
//						WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
//				if (wrapper != null) {
//	
//					//STICKY
//					if(wrapper.getContentAsByteArray().length == 0) {
//						if(StringUtils.indexOf(request.getContentType(), MediaType.APPLICATION_FORM_URLENCODED_VALUE) != -1) {
//							request.getParameterMap();
//						}
//					}
//	
//					byte[] buf = wrapper.getContentAsByteArray();
//					if (buf.length > 0) {
//						int length = Math.min(buf.length, getMaxPayloadLength());
//						String payload;
//						try {
//							payload = new String(buf, 0, length, wrapper.getCharacterEncoding());
//						}
//						catch (UnsupportedEncodingException ex) {
//							payload = "[unknown]";
//						}
//						msg.append(";\npayload=").append(payload);
//						if (buf.length != length) {
//							msg.append("...");
//						}
//					}
//				}
//			}
//		}
//
//		msg.append(";");
//		msg.append(suffix);
//		return msg.toString();
//	}
//
//
//	protected String createResponseMessage(HttpServletRequest request, HttpServletResponse response, String prefix, String suffix) {
//		StringBuilder msg = new StringBuilder();
//		if (response instanceof ContentCachingResponseWrapper) {
//			String messageId = (String)request.getAttribute("__message_id");
//			long startTime = (Long)request.getAttribute("__start_time");
//			long endTime = (Long)request.getAttribute("__end_time");
//			msg.append(prefix);
//			msg.append("message-id=").append(messageId);
//			msg.append(";start-time=").append(DateUtil.asLocalDateTime(startTime).format(DATE_FORMAT));
//			msg.append(";end-time=").append(DateUtil.asLocalDateTime(endTime).format(DATE_FORMAT));
//			msg.append(";uri=").append(request.getRequestURI());
//			String client;
//			if (this.isIncludeQueryString()) {
//				client = request.getQueryString();
//				if (client != null) {
//					msg.append('?').append(client);
//				}
//			}
//
//			String user;
//			if (this.isIncludeClientInfo()) {
//				client = request.getRemoteAddr();
//				if (StringUtils.length(client) > 0) {
//					msg.append(";client=").append(client);
//				}
//
//				HttpSession session = request.getSession(false);
//				if (session != null) {
//					msg.append(";session=").append(session.getId());
//				}
//
//				user = request.getRemoteUser();
//				if (user != null) {
//					msg.append(";user=").append(user);
//				}
//			}
//
//			if (this.isIncludeHeaders()) {
//				Map<String, String> headers = new HashMap();
//				Iterator iterator = response.getHeaderNames().iterator();
//
//				while(iterator.hasNext()) {
//					user = (String)iterator.next();
//					headers.put(user, response.getHeader(user));
//				}
//
//				if (StringUtils.equals(this.headersFormat, "json")) {
//					try {
//						msg.append(";\nheaders=").append(this.mapper.writeValueAsString(headers));
//					} catch (JsonProcessingException e) {
//						e.printStackTrace();
//					}
//				} else {
//					msg.append(";\nheaders=").append(headers);
//				}
//			}
//
//			if (this.isIncludePayload()) {
//				ContentCachingResponseWrapper wrapper = (ContentCachingResponseWrapper)WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
//				if (wrapper != null) {
//					byte[] buf = wrapper.getContentAsByteArray();
//					if (buf.length > 0) {
//						int length = Math.min(buf.length, this.getMaxPayloadLength());
//
//						String payload;
//						try {
//							payload = new String(buf, 0, length, wrapper.getCharacterEncoding());
//						} catch (UnsupportedEncodingException e) {
//							payload = "[unknown]";
//						}
//
//						msg.append(";\npayload=").append(payload);
//						if (buf.length != length) {
//							msg.append("...");
//						}
//
//						try {
//							wrapper.copyBodyToResponse();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//
//			msg.append(";");
//			msg.append(suffix);
//		}
//
//		return msg.toString();
//	}
//}