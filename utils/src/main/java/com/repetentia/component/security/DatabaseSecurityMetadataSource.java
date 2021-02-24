package com.repetentia.component.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        String url = fi.getRequestUrl();
//        Set<Entry<RequestMatcher, List<ConfigAttribute>>> entrySet = requestMap.entrySet();
//        for (Entry<RequestMatcher, List<ConfigAttribute>> entry:entrySet) {
//            RequestMatcher requestMatcher = entry.getKey();
//            if (requestMatcher.matches(request)) {
//                return entry.getValue();
//            }
//        }
//        return null;
        if (url.indexOf("system") > -1) {
            return SecurityConfig.createListFromCommaDelimitedString("ROLE_USER,ROLE_ANONYMOUS");
        } else {
            return SecurityConfig.createListFromCommaDelimitedString("ROLE_USER");
        }

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        log.info("###############################");
        log.info("# init");
        log.info("###############################");
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
