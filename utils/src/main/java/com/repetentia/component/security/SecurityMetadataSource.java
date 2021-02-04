//package com.repetentia.component.security;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.stereotype.Component;
//
//import kr.co.ydns.grip.model.entity.extended.MenuAuthDto;
//
//@Component
//public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//	private static final Logger LOGGER = LogManager.getLogger(SecurityMetadataSource.class);
//
//	Map<RequestMatcher, Set<ConfigAttribute>> requestMap;
//
//	@Override
//	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//		Collection<ConfigAttribute> configAttributes = null;
//		HttpServletRequest request = ((FilterInvocation)object).getRequest();
//		for (Entry<RequestMatcher, Set<ConfigAttribute>> entry: requestMap.entrySet()) {
//			LOGGER.trace("path rules url : {} - request url : {} - configAttributes : {}", entry.getKey(), request.getRequestURI(), configAttributes);
//			if (entry.getKey().matches(request)) {
//				configAttributes = entry.getValue();
//				break;
//			}
//		}
//		return configAttributes;
//	}
//
//	/**
//	 * 모든 권한 정보
//	 * return Collection<ConfigAttribute>
//	 */
//	@Override
//	public Collection<ConfigAttribute> getAllConfigAttributes() {
//		Set<ConfigAttribute> allConfigAttributes = reLoadUrlRoles();
//		return allConfigAttributes;
//	}
//	/**
//	 * 권한정보 리로드
//	 * return Set<ConfigAttribute>
//	 */
//	public Set<ConfigAttribute> reLoadUrlRoles() {
//		Set<ConfigAttribute> allConfigAttributes = new HashSet<ConfigAttribute>();
//		this.requestMap = new LinkedHashMap<RequestMatcher, Set<ConfigAttribute>>();
//		List<MenuAuthDto> menuAuthDtos = extendedMapper.findAllMenus();
//		Map<String, Set<ConfigAttribute>> roleUrlMap = mapRoles(menuAuthDtos);
//
//		LOGGER.info("################################################################################################");
//		for (Entry<String, Set<ConfigAttribute>> entry:roleUrlMap.entrySet()) {
//			StringBuilder url = new StringBuilder();
//			url.append(entry.getKey());
//			url.append("*/**");
//			Set<ConfigAttribute> configAttributes = entry.getValue();
//			allConfigAttributes.addAll(configAttributes);
//			requestMap.put(new AntPathRequestMatcher(url.toString()), configAttributes);
//			LOGGER.info("# URL Security - grant : {}  to : {}", url, configAttributes);
//		}
//		LOGGER.info("################################################################################################");
//		return allConfigAttributes;
//	}
//	// 롤과 url을 매핑
//	private Map<String, Set<ConfigAttribute>> mapRoles(List<MenuAuthDto> menuAuthDtos) {
//		Map<String, Set<ConfigAttribute>> map = new HashMap<String, Set<ConfigAttribute>>();
//		for (MenuAuthDto menuAuthDto:menuAuthDtos) {
//			String urlNm = menuAuthDto.getUrlNm();
//			String authGroupCode = menuAuthDto.getAuthGroupCode();
//			if ( authGroupCode == null ) {
//				continue;
//			}
//			Set<ConfigAttribute> configAttributes = map.get(urlNm);
//			ConfigAttribute configAttribute = new SecurityConfig("ROLE_"+authGroupCode);
//			if (configAttributes == null) configAttributes = new HashSet<ConfigAttribute>();
//			configAttributes.add(configAttribute);
//			map.put(urlNm, configAttributes);
//		}
//		return map;
//	}
//
//	@Override
//	public boolean supports(Class<?> clazz) {
//		return FilterInvocation.class.isAssignableFrom(clazz);
//	}
//}
