package com.repetentia.component.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.ClassUtils;

import com.repetentia.component.log.RtaLogFactory;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    private static final Logger log = RtaLogFactory.getLogger(JwtAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        Jwt jwt = authenticationToken.getToken();
        String subj = jwt.getSubject();
//        String principal = (String) authenticationToken.getPrincipal();
//        String credential = (String) authenticationToken.getCredentials();
//        authenticationToken.getAuthorities();
//        log.info("# principal - {} : credentials - {}", principal, credential);
//        Jwt jwt = authenticationToken.getToken();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority ga = new SimpleGrantedAuthority("ROLE_jwt");
//        GrantedAuthority ra = new SimpleGrantedAuthority("ROLE_ANONYMOUS");

        authorities.add(ga);
        JwtAuthenticationToken authenticatedToken = new JwtAuthenticationToken(jwt, authorities);

        log.info("# AUTHENTICATED");
        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ClassUtils.isAssignable(JwtAuthenticationToken.class, authentication);
    }
}