package com.repetentia.component.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {
    private UrlSecuritySource urlSecuritySource;
    private Map<RequestMatcher, List<ConfigAttribute>> requestMap;

    public DatabaseSecurityMetadataSource(UrlSecuritySource urlSecuritySource) {
        this.urlSecuritySource = urlSecuritySource;

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        HttpServletRequest request = fi.getRequest();

        Set<Entry<RequestMatcher, List<ConfigAttribute>>> entrySet = requestMap.entrySet();
        for (Entry<RequestMatcher, List<ConfigAttribute>> entry:entrySet) {
            RequestMatcher requestMatcher = entry.getKey();
            if (requestMatcher.matches(request)) {
                List<ConfigAttribute> list = entry.getValue();
                log.trace("# NEED ROLE {} FOR URL {} - {}", list, fi.getRequestUrl());
                return list;
            }
        }
        log.trace("# NO URL MAPPING FOUND FOR URL - {}", fi.getRequestUrl());
        return SecurityConfig.createList("ROLE_ANONYMOUS");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.requestMap = this.urlSecuritySource.loadUrlSecuritySource();
    }

}
