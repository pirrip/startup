package com.repetentia.component.security.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.ClassUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        String principal = (String) authenticationToken.getPrincipal();
        String credential = (String) authenticationToken.getCredentials();
        authenticationToken.getAuthorities();
        log.info("# principal - {} : credentials - {}", principal, credential);
        Jwt jwt = authenticationToken.getToken();
        JwtAuthenticationToken authenticatedToken = new JwtAuthenticationToken(jwt, null);
        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ClassUtils.isAssignable(JwtAuthenticationToken.class, authentication);
    }
}