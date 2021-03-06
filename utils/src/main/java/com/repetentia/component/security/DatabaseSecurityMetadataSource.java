package com.repetentia.component.security;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.repetentia.component.log.RtaLogFactory;
import com.repetentia.support.log.Marker;

public class DatabaseSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static final Logger log = RtaLogFactory.getLogger(DatabaseSecurityMetadataSource.class);

    private UrlSecuritySource urlSecuritySource;
    private Map<RequestMatcher, List<ConfigAttribute>> requestMap;

    public DatabaseSecurityMetadataSource(UrlSecuritySource urlSecuritySource) {
        this.urlSecuritySource = urlSecuritySource;

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (this.requestMap == null)
            loadRequestMap();
        FilterInvocation fi = (FilterInvocation) object;
        HttpServletRequest request = fi.getRequest();

        Set<Entry<RequestMatcher, List<ConfigAttribute>>> entrySet = requestMap.entrySet();
        for (Entry<RequestMatcher, List<ConfigAttribute>> entry : entrySet) {
            RequestMatcher requestMatcher = entry.getKey();
            if (requestMatcher.matches(request)) {
                List<ConfigAttribute> list = entry.getValue();
                log.trace(Marker.AUTH, "# {} FOR URL [{}] - uid : [{}]", list, fi.getRequestUrl(), getUID(request));
                return list;
            }
        }
        log.trace(Marker.AUTH, "# ROLE_ANONYMOUS FOR URL [{}] - uid : [{}]", fi.getRequestUrl(), getUID(request));
        return SecurityConfig.createList("ROLE_ANONYMOUS");
//        return null;
    }

    private String getUID(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return "ANONYMOUS";
        } else {
            return principal.getName();
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public void loadRequestMap() {
        this.requestMap = this.urlSecuritySource.loadUrlSecuritySource();
    }

}
