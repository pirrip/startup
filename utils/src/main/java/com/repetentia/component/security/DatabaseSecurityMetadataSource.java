package com.repetentia.component.security;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
	    FilterInvocation fi = (FilterInvocation) object;
	    String url = fi.getRequestUrl();
	    
	    log.info("# REQUESTED url - {}", url);
	    
	    String[] stockArr = {"ROLE_ADMIN"};
	    
		return  SecurityConfig.createList(stockArr);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
		
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
