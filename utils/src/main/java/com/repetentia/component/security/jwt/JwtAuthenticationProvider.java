package com.repetentia.component.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.ClassUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

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
//        authorities.add(ra);
        JwtAuthenticationToken authenticatedToken = new JwtAuthenticationToken(jwt, authorities);
//        UsernamePasswordAuthenticationToken authenticatedToken = new UsernamePasswordAuthenticationToken(subj, "pwd", authorities);

        log.info("# AUTHENTICATED");
        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ClassUtils.isAssignable(JwtAuthenticationToken.class, authentication);
    }
}